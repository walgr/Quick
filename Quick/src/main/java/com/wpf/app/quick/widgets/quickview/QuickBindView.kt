package com.wpf.app.quick.widgets.quickview

import android.content.Context
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
    @LayoutRes open val layoutId: Int,
    private var dealBind: Boolean = true
) : QuickItemView(mContext, attributeSet, defStyleAttr), Bind {

    init {
        initView()
    }

    private var mView: View? = null
    var index: Int = -1

    open fun initView() {
        post {
            val parent = parent as? ViewGroup ?: return@post
            if (this.layoutId == 0) return@post
            mView = inflate(context, layoutId, null)
            val thisIndex = parent.indexOfChild(this)
            parent.addView(mView, thisIndex)
            visibility = GONE
            onCreateViewHolder()
            onBindViewHolder(thisIndex)
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
        //隐藏当前view
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
//        val specSize = MeasureSpec.getSize(heightMeasureSpec)
        if (specMode == MeasureSpec.EXACTLY || specMode == MeasureSpec.UNSPECIFIED)
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        else super.onMeasure(widthMeasureSpec, 0)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //隐藏当前view
        super.onLayout(changed, left, 0, right, 0)
    }
}