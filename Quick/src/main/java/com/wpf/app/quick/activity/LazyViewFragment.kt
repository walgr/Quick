package com.wpf.app.quick.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.AutoGet

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class LazyViewFragment @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    override val layoutView: View? = null,
    titleName: String = ""
) : QuickFragment(layoutId, layoutView, titleName = titleName) {

    protected var rootView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (rootView == null) {
            if (layoutView != null) {
                rootView = layoutView
            } else if (layoutId != 0) {
                rootView = inflater.inflate(layoutId, null)
            }
            QuickBind.bind(this)
            initView(rootView)
        }
        return rootView!!
    }
}