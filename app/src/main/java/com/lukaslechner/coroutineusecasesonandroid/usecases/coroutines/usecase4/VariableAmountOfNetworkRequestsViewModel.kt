package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                mockApi.getRecentAndroidVersions()
                    .map { mockApi.getAndroidVersionFeatures(it.apiLevel) }
                    .let {
                        uiState.value = UiState.Success(it)
                    }
            }
        } catch (e: Exception) {
            uiState.value = UiState.Error("Nope")
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                mockApi.getRecentAndroidVersions()
                    .map { async { mockApi.getAndroidVersionFeatures(it.apiLevel) } }
                    .awaitAll()
                    .let {
                        uiState.value = UiState.Success(it)
                    }
            }
        } catch (e: Exception) {
            uiState.value = UiState.Error("Nope")
        }
    }
}