package com.base.retrofit

import android.app.Dialog
import android.content.Context


class RetrofitConfig private constructor() {
    private var callBack: CallBack? = null

    fun getBaseUrl(): String {
        return callBack!!.getBaseUrl()
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    fun getLoadingDialog(context: Context, message: String, cancelable: Boolean): Dialog {
        return callBack!!.getLoadingDialog(context, message, cancelable)
    }


    interface CallBack {
        fun getBaseUrl(): String
        fun getLoadingDialog(context: Context, message: String, cancelable: Boolean): Dialog
    }

    companion object {

        private var instance: RetrofitConfig? = null;

        fun getInstance(): RetrofitConfig {
            //判断是否初始化，不可以判空，否则报错
            if (instance==null) {
                instance = RetrofitConfig()
            }
            return instance as RetrofitConfig
        }
    }
}
