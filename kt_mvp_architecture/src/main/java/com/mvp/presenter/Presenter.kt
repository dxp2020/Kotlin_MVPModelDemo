package com.mvp.presenter


interface Presenter<V> {

    fun attachView(v: V)

    fun detachView()

}