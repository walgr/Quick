package com.wpf.app.quick.widgets.selectview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.wpf.app.quick.widgets.recyclerview.QuickSelectAdapter
import com.wpf.app.quick.widgets.recyclerview.QuickSelectRecyclerView
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectCallback
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectOnChange
import com.wpf.app.quick.widgets.recyclerview.listeners.SetSelectChange
import com.wpf.app.quick.widgets.selectview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.selectview.data.QuickParentSelectData
import com.wpf.app.quick.widgets.selectview.helper.ParentChildDataHelper

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

    var mOnSelectCallback: OnSelectCallback? = null

    private val selectViewList = mutableListOf<QuickSelectRecyclerView>()

    init {
        initView()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        children.forEachIndexed { index, it ->
            if (it is QuickSelectRecyclerView) {
                addSelectRecyclerView(it, index)
                addListener()
            }
        }
    }

    private fun initView() {
        orientation = HORIZONTAL
        weightList?.let {
            (weightList.indices).forEachIndexed { index, weightPos ->
                addView(addSelectRecyclerView(null, index), LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ).also { it.weight = weightList[weightPos] })
            }
            addListener()
        }
    }

    private fun addListener() {
        selectViewList[selectViewList.size - 1].setOnSelectChangeListener(object :
            OnSelectOnChange {
            override fun onSelectChange() {
                mOnSelectCallback?.onSelectResult(getSelectList())
            }
        })
        selectViewList[selectViewList.size - 1].addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val selectAdapter: QuickSelectAdapter =
                    recyclerView.adapter as? QuickSelectAdapter ?: return
                val curTopPos = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0))
                if (curTopPos >= 0 && curTopPos < selectAdapter.size()) {
                    val curTopData =
                        selectAdapter.getRealTypeData<QuickChildSelectData>()?.get(curTopPos)
                    if (curTopData?.isInOne != true) return
                    val parentDataSize: Int = selectAdapter.parentSelectAdapter?.size() ?: 0
                    val parentPos: Int = selectAdapter.parentSelectAdapter?.getDataPos(curTopData.parent) ?: -1
                    if (parentPos in 0 until parentDataSize) {
                        selectAdapter.parentSelectAdapter?.curClickData = curTopData.parent
                        selectAdapter.parentSelectAdapter?.notifyItemChange()
                    }
                }
            }
        })
    }

    fun getSelectList(): List<QuickChildSelectData>? {
        if (selectViewList.isEmpty()) return null
        return selectViewList[0].getRealTypeData<QuickChildSelectData>()?.flatMap {
            it.childList ?: arrayListOf()
        }?.filter { it.isSelect }
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

    /**
     * @param childInOne 是否展示所有子项 只支持父子2级数据
     */
    fun setData(dataList: List<QuickParentSelectData>, childInOne: Boolean = false) {
        ParentChildDataHelper.initData(dataList, childInOne)
        selectViewList[0].setNewData(dataList)
        selectViewList[0].getSelectAdapter().curClickData = dataList[0]
        selectViewList[0].getSelectAdapter().notifyItemChanged(0)
        selectViewList[1].setNewData(dataList[0].childList)
    }

    fun bindResult(setChange: SetSelectChange?) {
        setChange?.setOnSelectChangeListener(object : OnSelectOnChange {
            override fun onSelectChange() {
                setNewSelectList(setChange.getSelectList())
            }
        })
    }

    fun setNewSelectList(newSelect: List<QuickChildSelectData>?) {
        selectViewList[0].clearAll(false)
        (selectViewList[0].getRealTypeData<QuickChildSelectData>())?.flatMap {
            it.childList ?: arrayListOf()
        }?.forEach { it ->
            val find = newSelect?.find { find ->
                find.id == it.id && find.parent == it.parent
            }
            if (find != null) {
                it.isSelect = true
            }
        }
        selectViewList.forEach {
            it.notifyItemChange()
        }
    }
}