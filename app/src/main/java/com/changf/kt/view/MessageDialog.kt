package com.changf.kt.view


import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

import com.base.dialog.BaseDialog
import com.changf.kt.R
import com.changf.kt.model.bean.MainModel
import com.changf.kt.model.bean.Weatherinfo
import com.changf.kt.presenter.MainPresenter

import butterknife.BindView
import butterknife.OnClick

class MessageDialog : BaseDialog(), MainPresenter.MainView {

    @BindView(R.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R.id.tv_content)
    lateinit var tvContent: TextView
    @BindView(R.id.bind_emailedite)
    lateinit var bindEmailedite: EditText
    @BindView(R.id.ll_title)
    lateinit var llTitle: RelativeLayout
    @BindView(R.id.tv_cancle)
    lateinit var tvCancle: TextView
    @BindView(R.id.tv_center_line)
    lateinit var tvCenterLine: View
    @BindView(R.id.tv_confirm)
    lateinit var tvConfirm: TextView
    private var mLoadDataListener: OnLoadDataListener? = null
    var mvpPresenter:MainPresenter?=null

    fun setTitle(title: String) {
        if (!TextUtils.isEmpty(title)) {
            this.tvTitle.text = title
            this.tvTitle.visibility = View.VISIBLE
        }
    }

    fun setContent(content: String) {
        this.tvContent.text = content
    }

    fun setCancle(str: String) {
        this.tvCancle.text = str
    }

    fun setConfirm(str: String) {
        this.tvConfirm.text = str
    }

    override fun handleRebuild(savedInstanceState: Bundle) {
        init()
    }

    @OnClick(R.id.tv_cancle, R.id.tv_confirm)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_cancle -> dismiss()
            R.id.tv_confirm -> mvpPresenter!!.loadData("101310222")
        }
    }

    override fun getDataSuccess(model: MainModel) {
        val weatherinfo = model.weatherinfo
        val showData = (resources.getString(R.string.city) + weatherinfo.city
                + resources.getString(R.string.wd) + weatherinfo.WD
                + resources.getString(R.string.ws) + weatherinfo.WS
                + resources.getString(R.string.time) + weatherinfo.time)
        if (mLoadDataListener != null) {
            mLoadDataListener!!.showData(showData)
            dismiss()
        }
    }

    override fun getDataFail(msg: String) {
        if (mLoadDataListener != null) {
            mLoadDataListener!!.showData(msg)
            dismiss()
        }
    }

    fun setLoadDataListener(onLoadDataListener: OnLoadDataListener) {
        mLoadDataListener = onLoadDataListener
    }

    interface OnLoadDataListener {
        fun showData(content: String)
    }

    override fun getLayoutId(): Int {
        return R.layout.common_dialog_layout
    }

    override fun attachPresenter() {
        mvpPresenter = MainPresenter(this)
    }

    override fun detachPresenter() {
        mvpPresenter?.detachView()
    }

}
