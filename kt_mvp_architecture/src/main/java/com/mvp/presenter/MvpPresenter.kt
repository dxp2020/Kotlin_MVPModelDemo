package com.mvp.presenter

import com.mvp.view.MvpActivity
import com.mvp.view.MvpDialog
import com.mvp.view.MvpFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable


open class MvpPresenter<V> : Presenter<V>, Serializable {
    var mDialog: MvpDialog? = null
    var mFragment: MvpFragment? = null
    var mActivity: MvpActivity? = null
    var mvpView: V? = null

    override fun attachView(view: V) {
        mvpView = view
        if (mvpView is MvpFragment) {
            mFragment = mvpView as MvpFragment?
            mActivity = mFragment!!.getActivity() as MvpActivity
        } else if (mvpView is MvpActivity) {
            mActivity = mvpView as MvpActivity?
        } else if (mvpView is MvpDialog) {
            mDialog = mvpView as MvpDialog?
            mActivity = mDialog!!.getActivity() as MvpActivity
        }
    }

    override fun detachView() {
        mvpView = null
    }

    fun <T> addSubscription(observable: Observable<T>, subscriber: Observer<T>) {
        val transformer: LifecycleTransformer<T>
        if (mDialog != null) {
            transformer = mDialog!!.bindUntilEvent(FragmentEvent.DESTROY)
        } else if (mFragment != null) {
            transformer = mFragment!!.bindUntilEvent(FragmentEvent.DESTROY)
        } else {
            transformer = mActivity!!.bindUntilEvent(ActivityEvent.DESTROY)
        }
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(subscriber)
    }

}