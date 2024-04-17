package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.CallSuper
import com.wpf.app.quickutil.other.GenericEx
import com.wpf.app.quickutil.other.getClassWithSuper
import com.wpf.app.quickutil.widget.QuickViewGroupNoAttrs

/**
 * 最多1个子View
 */
abstract class Only1Child<T : View> @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickViewGroupNoAttrs<RelativeLayout>(mContext, attributeSet, defStyleAttr, true) {

    @CallSuper
    override fun onFinishInflate() {
        super.onFinishInflate()
        if (getRealChildCount() > 1) {
            throw RuntimeException("只允许1个子View")
        }
        getChildAtInShadow(0)?.let {
            if (!it.javaClass.getClassWithSuper().contains(GenericEx.get0Clazz(this))) {
                throw RuntimeException("子View类型不匹配")
            }
        }

    }
}