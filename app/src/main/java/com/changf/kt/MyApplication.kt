package com.changf.kt

import com.base.BaseApplication
import com.base.retrofit.RetrofitConfig
import com.changf.kt.retrofit.RetrofitCallback

class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        RetrofitConfig.getInstance().setCallBack(RetrofitCallback())
    }

}
