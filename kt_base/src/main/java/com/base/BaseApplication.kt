package com.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.blankj.utilcode.util.AppUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

open class BaseApplication : MultiDexApplication(){
    private var mRefWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        mApplication = this

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        if (AppUtils.isAppDebug()) {
            mRefWatcher = LeakCanary.install(this)
        }
    }

    companion object{
        private var mApplication: Application? = null

        fun getApplication(): Application? {
            return mApplication
        }

        fun getRefWatcher(context: Context): RefWatcher? {
            val application = context.applicationContext as BaseApplication
            return application.mRefWatcher
        }
    }

}