package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Exception

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        usingWithTimeoutOrNull(timeout)
    }

    private fun usingWithTimeout(timeout: Long) {
        viewModelScope.launch {
            try {
                val result = withTimeout(timeout) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(result)

            } catch (e: Exception) {
                uiState.value = when (e) {
                    is TimeoutCancellationException -> UiState.Error("timeout")
                    else -> UiState.Error("something went wrong :(")
                }
            }
        }
    }

    private fun usingWithTimeoutOrNull(timeout: Long) {
        viewModelScope.launch {
            try {
                uiState.value = withTimeoutOrNull(timeout) {
                    api.getRecentAndroidVersions()
                }?.let {
                    UiState.Success(it)
                } ?: run {
                    UiState.Error("timeout ($timeout)!")
                }
            } catch (e: Exception) {
                UiState.Error("something went wrong :(")
            }
        }
    }

}