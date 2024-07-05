package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import android.view.View
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.helper.binddatahelper.Select2CheckBox
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickClickData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.utils.LogUtil
import com.wpf.app.quickutil.helper.generic.forceTo
import com.wpf.app.quickutil.run.runOnView
import com.wpf.app.quickwidget.selectview.QuickSelectAdapter
import com.wpf.app.quickwidget.selectview.data.QuickChildSelectData
import com.wpf.app.quickwidget.selectview.data.QuickParentSelectData

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class ParentSelectItem : QuickParentSelectData(
    layoutId = R.layout.holder_select_parent_item,
    canCancel = false,
    canClickAgain = false
) {

    @Transient
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnView { "" + name + super.id + "(${getChildSelectSize()})" }

    override fun onSelectChildChange(selectList: MutableList<QuickChildSelectData>?) {
        super.onSelectChildChange(selectList)
        getAdapter()?.notifyItemChanged(this)
    }

    override fun asTitleViewInChild(): ParentTitleSelectItem {
        return ParentTitleSelectItem(name + super.id).also {
            it.id = super.id
        }
    }

    override fun onBindViewHolder(
        adapter: QuickSelectAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int,
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
        getView()?.isSelected = isSelect
    }
}

class ParentTitleSelectItem(
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    val title: String? = null,
) : QuickParentSelectData(
    isSuspension = true,
    layoutId = R.layout.holder_select_parent_title_item
) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : QuickItemData> clone(): T {
        val newData = super.clone<ParentTitleSelectItem>()
        newData.forceTo<QuickClickData>().itemClick = this@ParentTitleSelectItem.itemClick
        return newData as T
    }

    private var isShowChild = true
    override fun onClick(view: View) {
        val positionStart = getDataPos()
        if (isShowChild) {
            LogUtil.e("收缩${title},位置:${positionStart}")
            if (parent?.childList?.isEmpty() == true) return
            parent?.childList?.takeLast(parent!!.childList!!.size - 1)?.let { removeList ->
                getAdapter()?.let {
                    it.getData()?.removeAll(removeList)
                    it.notifyItemRangeRemoved(positionStart + 1, removeList.size)
                }
            }
            isShowChild = false
        } else {
            LogUtil.e("展开${title},位置:${positionStart}")
            parent?.childList?.takeLast(parent!!.childList!!.size - 1)?.let { removeList ->
                getAdapter()?.let {
                    it.getData()?.addAll(positionStart + 1, removeList)
                    it.notifyItemRangeInserted(positionStart + 1, removeList.size)
                }
            }
            isShowChild = true
        }
    }
}

open class SelectItem :
    QuickChildSelectData(layoutId = R.layout.holder_select_item, isGlobal = false) {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.select, helper = Select2CheckBox::class)
    override var isSelect = false

    @Transient
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnView { name + super.id + "属于:父" + this@SelectItem.parent?.id }
}