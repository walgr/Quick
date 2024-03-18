package com.wpf.app.quickbind.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickutil.other.forceTo

fun getFragment(
    obj: Any,
    baseFragment: BindBaseFragment,
    position: Int,
): BindBaseFragment {
    if (obj is Activity) {
        baseFragment.forceTo<Fragment>().arguments =
            baseFragment.getInitBundle(obj, position)
    }
    if (obj is Fragment) {
        (baseFragment as Fragment).arguments =
            baseFragment.getInitBundle(obj, position)
    }
    return baseFragment
}