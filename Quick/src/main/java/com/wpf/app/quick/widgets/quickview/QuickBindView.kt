package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.QuickBind.dealInPlugins
import com.wpf.app.quickbind.interfaces.Bind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @LayoutRes
    private val layoutId: Int,
    private var dealBind: Boolean = true
) : QuickItemView(mContext, attributeSet, defStyleAttr), Bind {

    private var mView: View? = null
    var index: Int = -1

    init {
        initView()
    }

    fun initView() {
        mView = inflate(context, this.layoutId, null)
        post {
            val parent = parent as? ViewGroup ?: return@post
            val thisIndex = parent.indexOfChild(this)
            parent.addView(mView, thisIndex)
            visibility = GONE
            onCreateViewHolder()
            onBindViewHolder(thisIndex)
            postInvalidate()
        }
    }

    open fun onCreateViewHolder() {
        if (dealBind) {
            QuickBind.bind(this)
        }
    }

    @CallSuper
    open fun onBindViewHolder(position: Int) {
        index = position
        if (dealBind) {
            dealInPlugins(this, null, QuickBind.bindPlugin)
        }
    }

    fun noBind() {
        this.dealBind = false
    }

    override fun getView(): View? {
        return mView
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        getView()?.measure(widthMeasureSpec, heightMeasureSpec)
        val viewMeasureWidth = getView()?.measuredWidth ?: 0
        val viewMeasureHeight = getView()?.measuredHeight ?: 0
        val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
        super.onMeasure(MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
            MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        getView()?.layout(left, top, right, bottom)
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        return getView()?.draw(canvas) ?: super.onDraw(canvas)
    }
}