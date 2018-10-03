package com.base.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import butterknife.ButterKnife
import butterknife.Unbinder
import com.base.BaseApplication
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.mvp.view.MvpDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseDialog : MvpDialog() {
    private var unbinder: Unbinder? = null
    private var mOnInitViewListener: OnInitViewListener? = null
    private var savedInstanceState: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (isFullScreen()) {
            dialog.window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            //处理Activity被杀死重建
            handleRebuild(savedInstanceState)
        } else {
            init()
        }
    }

    /**
     * 初始化
     */
    fun init() {
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        //注册ButterKnife
        unbinder = ButterKnife.bind(this, mRootView!!)
        initView()
        initData()
        initEvent()
    }

    /**
     * 反初始化
     */
    private fun uninit() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        if (unbinder != null) {
            unbinder!!.unbind()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isFullScreen()) {
            val window = dialog.window
            val params = window!!.attributes
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = params
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uninit()

        //监控内存泄露
        if (AppUtils.isAppDebug()) {
            try {
                val refWatcher = BaseApplication.getRefWatcher(activity!!)
                refWatcher?.watch(this)
            } catch (e: Exception) {
                LogUtils.e(e)
            }

        }
    }

    override fun attachPresenter() {}
    override fun detachPresenter() {}

    open fun initData() {}
    open fun initEvent() {}
    open fun initView() {
        if (mOnInitViewListener != null) {
            mOnInitViewListener!!.initView(mRootView)
        }
    }

    open fun isFullScreen():Boolean{
        return false
    }

    interface OnInitViewListener {
        fun initView(view: View?)
    }

    fun onInitViewListener(mOnInitView: OnInitViewListener) {
        this.mOnInitViewListener = mOnInitView
    }

    //处理页面重建
    open fun handleRebuild(savedInstanceState: Bundle) {
        this.savedInstanceState = savedInstanceState
        init()
    }

    //event bus 事件处理，必须重写
    @Subscribe
    fun onEventMainThread(pIntent: Intent) {
    }
}
