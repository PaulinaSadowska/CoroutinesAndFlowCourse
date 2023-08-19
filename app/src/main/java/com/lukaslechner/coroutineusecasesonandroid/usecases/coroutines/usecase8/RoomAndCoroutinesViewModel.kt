package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        uiState.value = UiState.Loading.LoadFromDb
        viewModelScope.launch {
            uiState.value = database.getAndroidVersions()
                .takeIf { it.isNotEmpty() }
                ?.let {
                    UiState.Success(DataSource.DATABASE, it.mapToUiModelList())
                } ?: run {
                UiState.Error(DataSource.DATABASE, "error")
            }

            try {
                uiState.value = UiState.Loading.LoadFromNetwork
                api.getRecentAndroidVersions()
                    .onEach { database.insert(it.mapToEntity()) }
                    .let {
                        uiState.value = UiState.Success(DataSource.NETWORK, it)
                    }
            } catch (_: Exception) {
                uiState.value = UiState.Error(DataSource.NETWORK, "error")
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            database.clear()
        }
    }
}


enum class DataSource(val dataSourceName: String) {
    DATABASE("Database"), NETWORK("Network")
}