package com.wpf.app.quick.widgets.quickview.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by 王朋飞 on 2022/9/1.
 *
 */
class MLinearLayout @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(mContext, attributeSet, defStyleAttr), QuickMeasure {

    @SuppressLint("WrongCall")
    override fun Measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    @SuppressLint("WrongCall")
    override fun Layout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        onLayout(changed, l, t, r, b)
    }

    @SuppressLint("WrongCall")
    override fun Draw(canvas: Canvas?) {
        onDraw(canvas)
    }
}