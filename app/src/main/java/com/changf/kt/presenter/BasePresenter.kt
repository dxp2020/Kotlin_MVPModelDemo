package com.changf.kt.presenter

import com.base.retrofit.ApiClient
import com.changf.kt.retrofit.ApiStores
import com.mvp.presenter.MvpPresenter

open class BasePresenter<V> : MvpPresenter<V>() {
    var apiStores = ApiClient.retrofit().create(ApiStores::class.java)
}
