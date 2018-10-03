package com.changf.kt.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.base.dialog.BaseDialog
import com.base.fragment.BaseFragment
import com.changf.kt.R
import com.changf.kt.model.bean.MainModel
import com.changf.kt.model.bean.Weatherinfo
import com.changf.kt.presenter.MainPresenter
import com.changf.kt.view.FullScreenDialog
import com.changf.kt.view.MessageDialog

import butterknife.BindView
import butterknife.OnClick

class TestDialogFragment : BaseFragment(), MainPresenter.MainView {

    @BindView(R.id.tv_weather)
    lateinit var tvWeather: TextView
    private var mMessageDialog: MessageDialog? = null
    private var mFullScreenDialog: FullScreenDialog? = null
    private var mvpPresenter:MainPresenter?=null

    override fun getLayoutId(): Int {
        return R.layout.layout_test_dialog_fragment
    }

    override fun handleRebuild(savedInstanceState: Bundle) {
        super.handleRebuild(savedInstanceState)
        mMessageDialog = fragmentManager?.findFragmentByTag("MessageDialog") as MessageDialog
        mFullScreenDialog = fragmentManager?.findFragmentByTag("FullScreenDialog") as FullScreenDialog
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

    private fun setMessageDialogListener(dialog: MessageDialog?) {
        if (dialog == null) {
            return
        }
        dialog.onInitViewListener(object : BaseDialog.OnInitViewListener {
            override fun initView(view: View?) {
                dialog.setContent("通过dialog获取天气信息")
                dialog.setConfirm("确定")
                dialog.setCancle("取消")
            }
        })
        dialog.setLoadDataListener(object : MessageDialog.OnLoadDataListener {
            override fun showData(content: String) {
                tvWeather.text = content
            }
        })
    }

    @OnClick(R.id.btn_show_dialog, R.id.btn_show_full_dialog)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_show_dialog -> {
                if (mMessageDialog == null) {
                    mMessageDialog = MessageDialog()
                    setMessageDialogListener(mMessageDialog)
                }
                mMessageDialog?.show(activity!!.supportFragmentManager, "MessageDialog")
            }
            R.id.btn_show_full_dialog -> {
                if (mFullScreenDialog == null) {
                    mFullScreenDialog = FullScreenDialog()
                }
                mFullScreenDialog?.show(activity!!.supportFragmentManager, "FullScreenDialog")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): TestDialogFragment {
            return TestDialogFragment()
        }
    }

    override fun attachPresenter() {
        mvpPresenter = MainPresenter(this)
    }

    override fun detachPresenter() {
        mvpPresenter?.detachView()
    }
}
