package com.changf.kt.retrofit


import com.changf.kt.model.bean.MainModel

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiStores {
    //加载天气
    @GET("adat/sk/{cityId}.html")
    fun loadDataByRetrofit(@Path("cityId") cityId: String): Call<MainModel>

    //加载天气
    @GET("adat/sk/{cityId}.html")
    fun loadDataByRetrofitRxJava(@Path("cityId") cityId: String): Observable<MainModel>
}
