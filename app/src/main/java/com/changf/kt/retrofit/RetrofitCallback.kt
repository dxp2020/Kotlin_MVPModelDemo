package com.changf.kt.retrofit

import android.app.Dialog
import android.content.Context

import com.base.dialog.LoadingDialog
import com.base.retrofit.RetrofitConfig
import com.changf.kt.model.bean.CommonValues

class RetrofitCallback : RetrofitConfig.CallBack {

    override fun getLoadingDialog(context: Context, message: String, cancelable: Boolean): Dialog {
        return LoadingDialog(context, message, cancelable)
    }

    override fun getBaseUrl(): String {
        return CommonValues.API_SERVER_URL
    }
}
