package com.wpf.app.quick.widgets.selectview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import com.wpf.app.quick.widgets.recyclerview.QuickSelectRecyclerView
import com.wpf.app.quick.widgets.recyclerview.data.QuickParentSelectData

/**
 * Created by 王朋飞 on 2022/9/5.
 *
 */
open class QuickMultistageSelectView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val weightList: List<Float>? = null,            //层级深度由数组长度确定 null由xml里自己确定子view
) : LinearLayout(mContext, attributeSet, defStyleAttr) {

    val selectViewList = mutableListOf<QuickSelectRecyclerView>()

    init {
        initView()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        children.forEachIndexed { index, it ->
            if (it is QuickSelectRecyclerView) {
                addSelectRecyclerView(it, index)
            }
        }
    }

    private fun initView() {
        orientation = HORIZONTAL
        weightList?.let {
            (0..weightList.size).forEachIndexed { index, weightPos ->
                addView(addSelectRecyclerView(null, index), LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ).also { it.weight = weightList[weightPos] })

            }
        }
    }

    private fun addSelectRecyclerView(
        selectRecyclerView: QuickSelectRecyclerView?,
        index: Int
    ): QuickSelectRecyclerView {
        val recyclerView = selectRecyclerView ?: QuickSelectRecyclerView(mContext = context)
        if (index > 0 && selectViewList.size > (index - 1)) {
            val parentSelectAdapter = selectViewList[index - 1].getSelectAdapter()
            parentSelectAdapter.childSelectAdapter = recyclerView.getSelectAdapter()
            recyclerView.getSelectAdapter().parentSelectAdapter = parentSelectAdapter
        }
        selectViewList.add(recyclerView)
        return recyclerView
    }

    fun setData(dataList: List<out QuickParentSelectData>) {
        selectViewList[0].setNewData(dataList)
        selectViewList[0].getSelectAdapter().curClickData = dataList[0]
        selectViewList[0].getSelectAdapter().notifyItemChanged(0)
        selectViewList[1].setNewData(dataList[0].childList)
    }
}