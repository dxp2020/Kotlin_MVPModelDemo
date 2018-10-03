package com.base.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.Unbinder
import com.base.BaseApplication
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.mvp.view.MvpActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 根据项目需求添加相关代码
 */
 abstract class BaseActivity: MvpActivity() {
    var mActivity: Activity? = null
    var unbinder: Unbinder? = null
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            handleRebuild(savedInstanceState)//处理Activity被杀死重建
        } else {
            init()
        }
    }

    /**
     * 初始化
     */
    fun init() {
        mActivity = this
        //全屏的情况下，隐藏导航栏
        if (ScreenUtils.isFullScreen(this) && BarUtils.isNavBarVisible(this)) {
            BarUtils.setNavBarVisibility(this, false)
        }
        //注册ButterKnife
        unbinder = ButterKnife.bind(this)
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
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

    override fun onDestroy() {
        super.onDestroy()
        uninit()
        //监控内存泄露
        if (AppUtils.isAppDebug()) {
            try {
                BaseApplication.getRefWatcher(applicationContext)?.watch(this)
            } catch (e: Exception) {
                LogUtils.e(e)
            }
        }
    }

    override fun attachPresenter() {}
    override fun detachPresenter() {}

    open fun initView() {}
    open fun initData() {}
    open fun initEvent() {}
    //处理页面重建
    open fun handleRebuild(savedInstanceState: Bundle) {
        this.savedInstanceState = savedInstanceState
        init()
    }

    //获取用于重建的Bundle，可用于重建或者判断是否重建
    fun getSavedInstanceState(): Bundle? {
        return savedInstanceState
    }
    //event bus 事件处理，必须重写
    @Subscribe
    open fun onEventMainThread(pIntent: Intent) {
    }
}
