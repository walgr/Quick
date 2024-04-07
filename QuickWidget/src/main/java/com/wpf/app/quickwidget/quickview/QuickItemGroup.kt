package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.base.bind.Bind
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/8/31.
 * layoutId
 */
open class QuickItemGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    addToParent: Boolean = true,
    @LayoutRes private var layoutId: Int = 0,
    open var viewType: Int = 0,
) : QuickViewGroup<T>(mContext, attrs, defStyleAttr, addToParent = addToParent),
    com.wpf.app.base.bind.Bind {

    private var mView: View? = null
    var position: Int = -1

    override fun init() {
        initView()
        initViewType()
        super.init()
    }

    open fun initViewType() {
        if (viewType == 0) {
            viewType = abs(javaClass.name.hashCode())
        }
    }

    open fun initView() {
        initLayoutIdByXml()
        if (layoutId == 0) return
        mView = inflate(context, this.layoutId, this)
    }

    private fun initLayoutIdByXml() {
        layoutId = attrSet.layoutRes
    }

    open fun onCreateViewHolder() {

    }

    open fun onBindViewHolder(position: Int) {

    }

    override fun getView(): View? {
        return mView
    }
}