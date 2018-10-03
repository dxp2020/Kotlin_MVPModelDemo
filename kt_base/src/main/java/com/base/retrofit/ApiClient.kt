package com.base.retrofit


import com.base.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
     var mRetrofit: Retrofit? = null

    fun retrofit(): Retrofit {
        if (mRetrofit == null) {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor)
            }
            val okHttpClient = builder.build()
            mRetrofit = Retrofit.Builder()
                    .baseUrl(RetrofitConfig.getInstance().getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
        }
        return mRetrofit as Retrofit
    }

}
