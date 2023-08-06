package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.rx

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private var disposable = Disposables.empty()
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        disposable = mockApi.getRecentAndroidVersions()
            .flatMap { it -> mockApi.getAndroidVersionFeatures(it.last().apiLevel) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    uiState.value = UiState.Success(it)
                },
                onError = {
                    uiState.value = UiState.Error("not found")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}