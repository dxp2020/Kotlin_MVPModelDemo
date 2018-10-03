package com.mvp.model.callback

import com.mvp.presenter.Presenter

//out 相当于java的extends 只能用来输出的，所以只能作为返回类型
interface MvpCallBack{
    fun getLayoutId(): Int
    fun attachPresenter()
    fun detachPresenter()
}
