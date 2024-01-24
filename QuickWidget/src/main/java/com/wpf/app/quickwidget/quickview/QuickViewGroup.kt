package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.wpf.app.quickutil.helper.attribute.AutoGetAttribute
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.other.GenericEx
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.view.matchLayoutParams
import com.wpf.app.quickwidget.R

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
open class QuickViewGroup<T : ViewGroup> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    addToParent: Boolean = true,
    childView: Array<View>? = null
) : QuickViewGroupNoAttrs<T>(context, attrs, defStyleAttr, addToParent, childView)