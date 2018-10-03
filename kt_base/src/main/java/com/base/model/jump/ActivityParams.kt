package com.base.model.jump;



import com.base.fragment.BaseFragment;

import java.io.Serializable;

class ActivityParams(val fragmentClazz: Class<out BaseFragment>, val fragmentParams: IFragmentParams<*>?) : Serializable