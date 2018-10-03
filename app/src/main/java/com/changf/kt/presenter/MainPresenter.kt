package com.changf.kt.presenter

import com.base.retrofit.SimpleSubscriber
import com.changf.kt.model.bean.MainModel

class MainPresenter(view: MainPresenter.MainView) : BasePresenter<MainPresenter.MainView>() {

    interface MainView {
        fun getDataSuccess(model: MainModel)
        fun getDataFail(msg: String)
    }

    init {
        attachView(view)
    }

    fun loadData(cityId: String) {
        addSubscription(apiStores.loadDataByRetrofitRxJava(cityId),
                object : SimpleSubscriber<MainModel>(mActivity!!) {
                    override fun onSuccess(result: MainModel) {
                        mvpView!!.getDataSuccess(result)
                    }

                    override fun onFailure(e: Throwable) {
                        mvpView!!.getDataFail("数据加载失败")
                    }
                })
    }


}
