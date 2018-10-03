package com.changf.kt.view



import com.base.dialog.BaseDialog
import com.changf.kt.R

class FullScreenDialog : BaseDialog() {

    override fun isFullScreen(): Boolean {
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_full_screen
    }

}
