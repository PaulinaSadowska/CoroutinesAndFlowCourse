package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber
import kotlin.Exception

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        viewModelScope.launch {
            try {
                uiState.value = retry(3) {
                    api.getRecentAndroidVersions()
                }.let { UiState.Success(it) }
            } catch (e: Exception) {
                uiState.value = UiState.Error("something went wrong :(")
            }
        }
    }

    private suspend fun <T> retry(
        numOfRetries: Int,
        initialDelay: Long = 100,
        maxDelayMillis: Long = 1_000,
        factor: Double = 2.0,
        operation: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(numOfRetries) {
            try {
                return operation()
            } catch (e: Exception) {
                Timber.e(e)
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
        }
        return operation()
    }
}