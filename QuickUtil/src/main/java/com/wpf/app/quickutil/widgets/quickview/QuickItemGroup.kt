package com.wpf.app.quickutil.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.widgets.quickview.helper.QuickViewGroupAttrSet
import com.wpf.app.quickutil.bind.Bind
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/8/31.
 * layoutId
 */
open class QuickItemGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    override val addToParent: Boolean = true,
    @LayoutRes private var layoutId: Int = 0,
    open var viewType: Int = 0,
) : QuickViewGroup<T>(mContext, attributeSet, defStyleAttr, addToParent = addToParent), Bind {

    private var mView: View? = null
    var position: Int = -1

    override fun init() {
        if (attrSet == null) {
            attrs?.let {
                attrSet = QuickViewGroupAttrSet(context, it)
            }
        }
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
        layoutId = attrSet?.layoutRes ?: layoutId
    }

    open fun onCreateViewHolder() {

    }

    open fun onBindViewHolder(position: Int) {

    }

    override fun getView(): View? {
        return mView
    }
}