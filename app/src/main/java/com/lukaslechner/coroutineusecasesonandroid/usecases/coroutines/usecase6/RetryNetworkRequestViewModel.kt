package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.Exception

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        viewModelScope.launch {
            try {
                uiState.value = retry(2) {
                    api.getRecentAndroidVersions()
                }.let { UiState.Success(it) }
            } catch (e: Exception) {
                uiState.value = UiState.Error("something went wrong :(")
            }
        }
    }

    private suspend fun <T> retry(numOfRetries: Int, operation: suspend () -> T): T {
        repeat(numOfRetries) {
            try {
                return operation()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        return operation()
    }
}