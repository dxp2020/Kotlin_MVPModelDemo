package com.base.model.jump


import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentManager
import com.base.R
import com.base.activity.CommonActivity
import com.base.fragment.BaseFragment

object Static {

    /**
     * 根据tag找到fragment，并清空栈中fragment之上的所有元素
     * @param manager
     * @param name  TAG
     */
    fun jumpToFragment(manager: FragmentManager, name: String) {
        manager.popBackStack(name, 0)//通过name能找到回退栈的特定元素，0表示只弹出该元素以上的所有元素
    }

    /**
     * 替换FrameLayout中的Fragment
     * 当前Activity中只有to这一个Fragment，其余的Fragment由FragmentManager管理，并未添加到Activity中
     * 通过tag将Fragment添加到返回栈
     * @param manager
     * @param to
     */
    fun jumpToFragment(manager: FragmentManager, to: BaseFragment) {
        val tag = to.javaClass.simpleName
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fl_fragment_container, to, tag)
        transaction.commitAllowingStateLoss()
    }

    /**
     * 显示Fragment-->"to"
     * @param from
     * @param to
     */
    fun jumpToFragment(from: BaseFragment, to: BaseFragment) {
        val tag = to.javaClass.simpleName
        val transaction = from.fragmentManager!!.beginTransaction()
        if (to.isAdded) {
            transaction.hide(from).show(to)
        } else {
            transaction.hide(from).add(R.id.fl_fragment_container, to, tag)
        }
        transaction.addToBackStack(tag)
        transaction.commitAllowingStateLoss()
    }

    /**
     * 启动一个Activity并显示Fragment
     * @param context
     * @param fragmentClass
     * @param params
     */
    fun jumpToFragment(context: Activity, fragmentClass: Class<out BaseFragment>, params: IFragmentParams<*>?) {
        val activityParams = ActivityParams(fragmentClass, params)
        val intent = Intent(context, CommonActivity::class.java)
        intent.putExtra(CommonActivity.PARAMS, activityParams)
        context.startActivity(intent)
    }

    /**
     * 启动一个Activity并显示Fragment
     * @param from
     * @param fragmentClass
     * @param requestCode
     */
    fun jumpToFragmentForResult(from: BaseFragment, fragmentClass: Class<out BaseFragment>, requestCode: Int) {
        val intent = Intent(from.activity, CommonActivity::class.java)
        intent.putExtra(CommonActivity.PARAMS, ActivityParams(fragmentClass, null))
        from.startActivityForResult(intent, requestCode)
    }

    /**
     * 启动一个Activity并显示Fragment
     * @param context
     * @param fragmentClass
     * @param mFragmentParams
     * @param requestCode
     */
    fun jumpToFragmentForResult(context: Activity, fragmentClass: Class<out BaseFragment>, mFragmentParams: IFragmentParams<*>, requestCode: Int) {
        val activityParams = ActivityParams(fragmentClass, mFragmentParams)
        val intent = Intent(context, CommonActivity::class.java)
        intent.putExtra(CommonActivity.PARAMS, activityParams)
        context.startActivityForResult(intent, requestCode)
    }

}
