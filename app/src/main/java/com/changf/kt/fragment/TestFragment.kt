package com.changf.kt.fragment

import android.view.View
import android.widget.TextView

import com.base.fragment.BaseFragment
import com.changf.kt.R
import com.changf.kt.model.bean.MainModel
import com.changf.kt.presenter.MainPresenter

import butterknife.BindView
import butterknife.OnClick

class TestFragment : BaseFragment(), MainPresenter.MainView {

    @BindView(R.id.tv_weather)
    lateinit var tvWeather: TextView

    var mvpPresenter:MainPresenter?=null

    override fun getLayoutId(): Int {
        return R.layout.layout_test_fragment
    }

    @OnClick(R.id.btn_obtain_weather)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_obtain_weather -> mvpPresenter?.loadData("101310222")
        }
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

    //伴生对象，非静态的，定义静态需要加JvmStatic
    companion object {
        @JvmStatic
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun attachPresenter() {
        mvpPresenter = MainPresenter(this)
    }

    override fun detachPresenter() {
        mvpPresenter?.detachView()
    }

}
