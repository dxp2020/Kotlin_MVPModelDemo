package com.base.activity


import android.content.Intent
import android.content.Intent.getIntent
import android.content.pm.ActivityInfo
import android.support.v4.app.Fragment
import com.base.R
import com.base.fragment.BaseFragment
import com.base.model.jump.ActivityParams
import com.base.model.jump.IFragmentParams
import com.mvp.presenter.MvpPresenter
import java.lang.reflect.InvocationTargetException

class CommonActivity : BaseActivity() {

    override fun getLayoutId(): Int{
        return R.layout.activity_common_layout
    }

    override fun initView() {
        val mParams = getIntent().getSerializableExtra(PARAMS) as ActivityParams
        try {
            var fragment: Fragment
            val targetClass = mParams.fragmentClazz
            val mIFragmentParams = mParams.fragmentParams
            if (mIFragmentParams != null) {
                //通过fragment的静态方法newInstance(IFragmentParams)构造Fragment
                fragment = targetClass.getMethod("newInstance", IFragmentParams::class.java).invoke(null, mParams.fragmentParams) as Fragment
            } else {
                //通过fragment的静态方法newInstance()构造Fragment
                fragment = targetClass.getMethod("newInstance").invoke(null) as Fragment
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment, targetClass.getSimpleName()).commit()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

    }

    override fun onBackPressed() {
        // 横屏转竖屏
        val state = this.getResources().getConfiguration().orientation
        if (state == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            return
        }
        // 在Fragment中拦截返回事件
        var isReturn = false
        for (fragment in getSupportFragmentManager().getFragments()) {
            if (fragment != null && fragment is BaseFragment) {//可能ull需判断
                    isReturn = fragment.onBackPressed()
            }
        }
        if (isReturn) {
            return
        }
        // 若未拦截交给Activity处理
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // super中回调Fragment的onActivityResult有问题，需自己回调Fragment的onActivityResult方法
        for (fragment in getSupportFragmentManager().getFragments()) {
            fragment?.onActivityResult(requestCode, resultCode, data)
            for (childFragment in fragment!!.getChildFragmentManager().getFragments()) {
                childFragment?.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // super中回调Fragment的onRequestPermissionsResult有问题，需自己回调Fragment的onRequestPermissionsResult方法
        for (fragment in getSupportFragmentManager().getFragments()) {
             fragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    companion object {
         val PARAMS = "params"
    }


}
