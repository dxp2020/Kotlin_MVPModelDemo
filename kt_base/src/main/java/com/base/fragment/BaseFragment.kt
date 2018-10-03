package com.base.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.base.BaseApplication
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.mvp.view.MvpActivity
import com.mvp.view.MvpFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 根据项目需求添加相关代码
 */
abstract class BaseFragment : MvpFragment() {
    var mActivity: MvpActivity? = null;
    private var unbinder: Unbinder? = null
    private var savedInstanceState: Bundle? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            handleRebuild(savedInstanceState)//处理Activity被杀死重建
        } else {
            init()
        }
    }

    fun init() {
        mActivity = activity as MvpActivity?
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
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        //注销ButterKnife
        if (unbinder != null) {
            unbinder!!.unbind()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //反初始化
        uninit()
        //监控fragment泄露
        if (AppUtils.isAppDebug()) {
            try {
                BaseApplication.getRefWatcher(context!!)?.watch(this)
            } catch (e: Exception) {
                LogUtils.e(e)
            }

        }
    }

    override fun attachPresenter() {}
    override fun detachPresenter() {}

    protected fun initView() {}
    protected fun initData() {}
    protected fun initEvent() {}
    //处理页面重建
    open fun handleRebuild(savedInstanceState: Bundle) {
        this.savedInstanceState = savedInstanceState
        init()
    }

    //返回键处理
    fun onBackPressed(): Boolean {
        return false
    }

    //event bus 事件处理，必须重写
    @Subscribe
    fun onEventMainThread(pIntent: Intent) {
    }
}
