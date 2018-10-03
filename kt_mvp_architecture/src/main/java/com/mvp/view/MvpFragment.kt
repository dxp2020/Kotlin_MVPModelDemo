package com.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvp.model.callback.MvpCallBack
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class MvpFragment : RxFragment(), MvpCallBack {
    val TAG = javaClass.simpleName
    var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        attachPresenter()
        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //用于页面被销毁重建
        if (mRootView != null) {
            (mRootView?.parent as ViewGroup).removeView(mRootView)
        }
        detachPresenter()
    }

}
