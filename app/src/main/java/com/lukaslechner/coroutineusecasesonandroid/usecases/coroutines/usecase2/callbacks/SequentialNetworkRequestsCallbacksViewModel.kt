package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    var call1: Call<List<AndroidVersion>>? = null
    var call2: Call<VersionFeatures>? = null

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        call1 = mockApi.getRecentAndroidVersions()
        call1!!.enqueue(object : Callback<List<AndroidVersion>> {
            override fun onResponse(
                p0: Call<List<AndroidVersion>>,
                response: Response<List<AndroidVersion>>
            ) {
                if (response.isSuccessful) {
                    val lastVersion = response.body()!!.last()
                    getVersionFeatures(lastVersion)
                } else {
                    uiState.value = UiState.Error("not found")
                }

            }

            override fun onFailure(p0: Call<List<AndroidVersion>>, p1: Throwable) {
                uiState.value = UiState.Error("not found")
            }

        })
    }

    private fun getVersionFeatures(lastVersion: AndroidVersion) {
        call2 = mockApi.getAndroidVersionFeatures(lastVersion.apiLevel)
        call2?.enqueue(object : Callback<VersionFeatures> {
            override fun onResponse(
                p0: Call<VersionFeatures>,
                response2: Response<VersionFeatures>
            ) {
                if (response2.isSuccessful) {
                    uiState.value = UiState.Success(response2.body()!!)
                } else {
                    uiState.value = UiState.Error("not found")
                }
            }

            override fun onFailure(p0: Call<VersionFeatures>, p1: Throwable) {
                uiState.value = UiState.Error("not found")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        call1?.cancel()
        call2?.cancel()
        call1 = null
        call2 = null
    }
}