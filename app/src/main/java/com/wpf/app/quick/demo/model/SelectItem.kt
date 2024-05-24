package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import android.view.View
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.helper.binddatahelper.Select2CheckBox
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickrecyclerview.data.QuickClickData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickutil.log.LogUtil
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.runOnView
import com.wpf.app.quickwidget.selectview.data.QuickChildSelectData
import com.wpf.app.quickwidget.selectview.data.QuickParentSelectData

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class ParentSelectItem : QuickParentSelectData(layoutId = R.layout.holder_select_parent_item, canCancel = false) {

    @Transient
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnView { "" + name + super.id + getParentName() + "(${getChildSelectSize()})" }

    override fun onSelectChildChange(selectList: List<QuickChildSelectData>?) {
        super.onSelectChildChange(selectList)
        title.run(getView()!!.findViewById(R.id.title))
        getViewHolder()?.bindingAdapterPosition?.let {
            getAdapter()?.notifyItemChanged(it)
        }
    }

    private fun getParentName(): String {
        parent?.id?.let { return "属于:父$it" }
        return ""
    }

    override fun onClickChange(clicked: Boolean) {
        super.onClickChange(clicked)
        getView()?.isSelected = clicked
    }

    override fun asTitleViewInChild(): ParentTitleSelectItem {
        return ParentTitleSelectItem(name + super.id).also {
            it.id = super.id
        }
    }
}

class ParentTitleSelectItem(
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    val title: String? = null
) : QuickParentSelectData(isSuspension = true, layoutId = R.layout.holder_select_parent_title_item) {

    override fun <T : QuickItemData> clone(): T {
        val newData = super.clone<ParentTitleSelectItem>()
        newData.forceTo<QuickClickData>().itemClick = this@ParentTitleSelectItem.itemClick
        return newData as T
    }

    private var isShowChild = true
    override fun onClick(view: View) {
        super.onClick(view)
        val positionStart = getViewPos() + 1
        if (isShowChild) {
            LogUtil.e("收缩${title}")
            if (parent?.childList?.isEmpty() == true) return
            parent?.childList?.takeLast(parent!!.childList!!.size - 1)?.let { removeList ->
                getAdapter()?.let {
                    it.getData()?.removeAll(removeList)
                    it.notifyItemRangeRemoved(positionStart, removeList.size)
                }
            }
            isShowChild = false
        } else {
            LogUtil.e("展开${title}")
            parent?.childList?.takeLast(parent!!.childList!!.size - 1)?.let { removeList ->
                getAdapter()?.let {
                    it.getData()?.addAll(positionStart, removeList)
                    it.notifyItemRangeInserted(positionStart, removeList.size)
                }
            }
            isShowChild = true
        }
    }
}

open class SelectItem : QuickChildSelectData(layoutId = R.layout.holder_select_item, isGlobal = false) {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.select, helper = Select2CheckBox::class)
    override var isSelect = false

    @Transient
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnView { name + super.id + "属于:父" + this@SelectItem.parent?.id }
}