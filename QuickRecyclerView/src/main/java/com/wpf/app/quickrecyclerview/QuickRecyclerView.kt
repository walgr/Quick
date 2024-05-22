package com.wpf.app.quickrecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.data.QuickFooterData
import com.wpf.app.quickrecyclerview.data.QuickHeaderData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.helper.toFooterViewData
import com.wpf.app.quickrecyclerview.helper.toHeaderViewData
import com.wpf.app.quickrecyclerview.helper.toViewData
import com.wpf.app.quickrecyclerview.listeners.DataAdapter
import com.wpf.app.quickrecyclerview.listeners.QuickAdapterListener
import com.wpf.app.quickrecyclerview.utils.SpaceItemDecoration
import com.wpf.app.quickrecyclerview.utils.SpaceType
import com.wpf.app.quickrecyclerview.widget.QuickFooterShadow
import com.wpf.app.quickrecyclerview.widget.QuickHeaderShadow
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.helper.children
import com.wpf.app.quickutil.other.asTo

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(mContext, attrs, defStyleAttr), DataAdapter {

    protected var mQuickAdapter: QuickAdapter = QuickAdapter()
    private val attr: QuickRecyclerViewAttr by lazy {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickRecyclerView)
    }

    init {
        this.initView()
        mQuickAdapter.setRecyclerView(this)
        adapter = mQuickAdapter
        dealAttrs()
    }

    open fun initView() {
        layoutManager = LinearLayoutManager(context)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (layoutManager is GridLayoutManager) {
            layoutManager?.asTo<GridLayoutManager>()?.apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val itemData = getDataWithHeaderFooter(position)
                        var isMatch = true
                        var isHeaderOrFooter = false
                        if (itemData is QuickHeaderData) {
                            isHeaderOrFooter = true
                            isMatch = itemData.isMatch
                        }
                        if (itemData is QuickFooterData) {
                            isHeaderOrFooter = true
                            isMatch = itemData.isMatch
                        }
                        return if (isHeaderOrFooter && isMatch) {
                            spanCount
                        } else {
                            1
                        }
                    }
                }
            }
        }
    }

    private fun dealAttrs() {
        if (attr.space != null) {
            setSpace(attr.space!!, attr.spaceType, attr.includeFirst, attr.includeLast)
        }
    }

    open fun setSpace(
        space: Int,
        spaceType: Int = SpaceType.Center.type,
        includeFirst: Boolean = false,
        includeLast: Boolean = false,
    ) {
        addItemDecoration(SpaceItemDecoration(space, spaceType, includeFirst, includeLast))
    }

    private var isFirst = true
    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        if (isFirst) {
            removeAllViews()
            isFirst = false
        }
        super.onMeasure(widthSpec, heightSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val allChild = children()
        allChild.forEach {
            when (it) {
                is QuickHeaderShadow -> {
                    getQuickAdapter().headerViews.add(
                        it.toHeaderViewData(
                            isSuspension = it.isSuspension,
                            isMatch = it.isMatch
                        )
                    )
                }

                is QuickFooterShadow -> {
                    getQuickAdapter().footerViews.add(it.toFooterViewData(isMatch = it.isMatch))
                }

                else -> {
                    getQuickAdapter().addData(it.toViewData())
                }
            }
        }
    }

    override fun getQuickAdapter(): QuickAdapter {
        return mQuickAdapter
    }

    open class QuickRecyclerViewAttr @JvmOverloads constructor(
        val space: Int? = null,
        val spaceType: Int = SpaceType.Center.type,
        val includeFirst: Boolean = false,
        val includeLast: Boolean = false,
    )
}

fun <T : QuickItemData> QuickRecyclerView.itemClick(click: (view: View, data: T?, position: Int) -> Unit) {
    getQuickAdapter().setQuickAdapterListener(object : QuickAdapterListener<T> {
        override fun onItemClick(view: View, data: T?, position: Int) {
            click.invoke(view, data, position)
        }
    })
}