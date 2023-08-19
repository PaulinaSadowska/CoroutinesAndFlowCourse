package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                listOf(27, 28)
                    .map {
                        async {
                            retryWithTimeout(retries = 2, timeout = 1_000) {
                                api.getAndroidVersionFeatures(it)
                            }
                        }
                    }
                    .awaitAll()
                    .let {
                        uiState.value = UiState.Success(it)
                    }
            } catch (e: Exception) {
                uiState.value = UiState.Error("nope")
            }
        }
    }

    private suspend fun <T> retryWithTimeout(retries: Int, timeout: Int, block: suspend () -> T) =
        retry(retries) {
            withTimeout(timeout.toLong()) {
                block()
            }
        }
}

private suspend fun <T> retry(
    numOfRetries: Int,
    delay: Long = 100,
    operation: suspend () -> T
): T {
    repeat(numOfRetries) {
        try {
            return operation()
        } catch (e: Exception) {
            Timber.e(e)
        }
        delay(delay)
    }
    return operation()
}