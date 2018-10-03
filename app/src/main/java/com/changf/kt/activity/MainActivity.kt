package com.changf.kt.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.base.activity.BaseActivity
import com.base.model.jump.Static
import com.changf.kt.R
import com.changf.kt.fragment.TestDialogFragment
import com.changf.kt.fragment.TestFragment
import com.changf.kt.model.bean.MainModel
import com.changf.kt.presenter.MainPresenter

class MainActivity : BaseActivity(), MainPresenter.MainView {

    @BindView(R.id.tv_weather)
    lateinit  var tvWeather: TextView;
    var mvpPresenter:MainPresenter?=null

    override fun handleRebuild(savedInstanceState: Bundle) {
        init()
    }

    override fun getDataSuccess(model: MainModel) {
        val weatherinfo = model.weatherinfo
        val showData = (resources.getString(R.string.city) + weatherinfo.city
                + resources.getString(R.string.wd) + weatherinfo.WD
                + resources.getString(R.string.ws) + weatherinfo.WS
                + resources.getString(R.string.time) + weatherinfo.time)
        tvWeather.text = showData
    }

    override fun getDataFail(msg: String) {
        tvWeather.text = msg
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    @OnClick(R.id.btn_mvp_activity, R.id.btn_mvp_fragment, R.id.btn_mvp_dialog)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_mvp_activity -> mvpPresenter?.loadData("101310222")
            R.id.btn_mvp_fragment -> {
                Static.jumpToFragment(mActivity!!, TestFragment::class.java, null);
            }
            R.id.btn_mvp_dialog -> {
                Static.jumpToFragment(mActivity!!, TestDialogFragment::class.java, null);
            }
        }
    }

    override fun attachPresenter() {
        mvpPresenter = MainPresenter(this)
    }

    override fun detachPresenter() {
        mvpPresenter?.detachView()
    }
}
