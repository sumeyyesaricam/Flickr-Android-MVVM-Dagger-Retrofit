package com.appcent.app.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


abstract class BaseViewModel<N> : ViewModel() {
    private val mIsLoading = MutableLiveData<Boolean>()
    lateinit var mNavigator: WeakReference<N>
    var mCompositeDisposable: CompositeDisposable
    init{
        mCompositeDisposable = CompositeDisposable()
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getNavigator(): N? {
        return mNavigator.get()
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference<N>(navigator)
    }
}