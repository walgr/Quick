package com.wpf.app.quickbind.interfaces

import android.app.Activity
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
abstract class BindBaseFragment : Fragment() {

    /**
     * 从ViewModelActivity获取数据
     */
    @NonNull
    open fun getInitBundle(viewModelActivity: BindViewModel<*>?, position: Int): Bundle? {
        return null
    }

    /**
     * 从Activity获取数据
     */
    @NonNull
    open fun getInitBundle(activity: Activity?, position: Int): Bundle? {
        return null
    }

    /**
     * 从Fragment获取数据
     */
    @NonNull
    open fun getInitBundle(fragment: Fragment?, position: Int): Bundle? {
        return null
    }

    @NonNull
    abstract fun getTitle(): String?
}