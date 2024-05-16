package com.wpf.app.quickutil.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
open class QuickViewGroup<T : ViewGroup> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    addToParent: Boolean = false,
    forceGenerics: Boolean = false          //强制泛型初始化
) : QuickViewGroupNoAttrs<T>(context, attrs, defStyleAttr, addToParent, forceGenerics)