package com.mvp.view

import android.os.Bundle
import com.mvp.model.callback.MvpCallBack
import com.trello.rxlifecycle2.components.support.RxFragmentActivity

abstract class MvpActivity : RxFragmentActivity(), MvpCallBack {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        attachPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        detachPresenter()
    }

}

