package com.wpf.app.quickbind.interfaces

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface BindBaseFragment {

    /**
     * 从Activity获取数据
     */
    fun getInitBundle(activity: Activity?, position: Int): Bundle? {
        return null
    }

    /**
     * 从Fragment获取数据
     */
    fun getInitBundle(fragment: Fragment?, position: Int): Bundle? {
        return null
    }

    fun getTitle(): String? {
        return null
    }
}