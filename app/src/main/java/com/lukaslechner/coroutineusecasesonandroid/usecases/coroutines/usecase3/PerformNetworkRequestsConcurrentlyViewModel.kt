package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                listOf(27, 28, 29)
                    .map { mockApi.getAndroidVersionFeatures(it) }
                    .let {
                        uiState.value = UiState.Success(it)
                    }
            }
        } catch (e: Exception) {
            uiState.value = UiState.Error("something went wrong")
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                listOf(27, 28, 29)
                    .map { async { mockApi.getAndroidVersionFeatures(it) } }
                    .awaitAll()
                    .let {
                        uiState.value = UiState.Success(it)
                    }
            }
        } catch (e: Exception) {
            uiState.value = UiState.Error("something went wrong")
        }
    }
}