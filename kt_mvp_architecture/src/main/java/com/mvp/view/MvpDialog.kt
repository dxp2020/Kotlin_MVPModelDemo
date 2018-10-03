package com.mvp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mvp.model.callback.MvpCallBack
import com.mvp.presenter.Presenter
import com.trello.rxlifecycle2.components.support.RxDialogFragment


abstract class MvpDialog: RxDialogFragment(), MvpCallBack {
    val TAG = javaClass.simpleName
    var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            (mRootView!!.parent as ViewGroup).removeView(mRootView)
        }
        detachPresenter()
    }


}
