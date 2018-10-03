package com.base.retrofit


import android.content.Context

import com.base.dialog.LoadingDialog

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class SimpleSubscriber<T> : Observer<T> {

    private var mLoading: LoadingDialog? = null
    private var mDisposable: Disposable? = null//相当于RxJava1.x中的Subscription,可用于解除订阅

    constructor() : super() {}

    constructor(context: Context) : super() {
        mLoading = LoadingDialog(context)
        mLoading!!.show()
    }

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onNext(t: T) {
        mLoading?.dismiss()
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        mLoading?.dismiss()
        onFailure(e)
        onComplete()
    }

    /**
     * 解除订阅
     */
    fun unSubscription() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
    }

    abstract fun onSuccess(result: T)

    open fun onFailure(e: Throwable) {}
    override fun onComplete() {
        unSubscription()
    }

}
