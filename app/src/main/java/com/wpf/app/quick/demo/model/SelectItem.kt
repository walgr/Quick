package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import android.os.Build
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.helper.binddatahelper.Select2CheckBox
import com.wpf.app.quick.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.widgets.selectview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.selectview.data.QuickParentSelectData
import com.wpf.app.quickbind.interfaces.runOnHolder
import com.wpf.app.quickutil.LogUtil

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class ParentSelectItem : QuickParentSelectData(layoutId = R.layout.holder_select_parent_item, canCancel = false) {

    @Transient
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnHolder { "" + name + id + getParentName() + "(${getChildSelectSize()})" }

    override fun onChildChange(selectList: List<QuickChildSelectData>?) {
        super.onChildChange(selectList)
        title.run(getView()!!.findViewById(R.id.title))
        getViewHolder()?.itemPosition?.let {
            getAdapter()?.notifyItemChanged(getViewHolder()?.itemPosition!!)
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

    override fun asTitleViewInChild(): QuickParentSelectData {
        return ParentTitleSelectItem(name + id).also {
            it.id = id
        }
    }
}

class ParentTitleSelectItem(
    @SuppressLint("NonConstantResourceId") @BindData2View(id = R.id.title, helper = Text2TextView::class)
    val title: String? = null
) : QuickParentSelectData(isSuspension = true, layoutId = R.layout.holder_select_parent_title_item) {

    private var isShowChild = true
    override fun onClick() {
//        super.onClick()
        LogUtil.e("点击了${title}")
        if (isShowChild) {
                getAdapter()?.getData()?.removeIf {
                    parent?.childList?.takeLast(parent!!.childList!!.size - 1)?.contains(it) ?: false
                }
            getAdapter()?.notifyItemChange()
            isShowChild = false
        } else {
            parent?.childList?.takeLast(parent!!.childList!!.size - 1)?.let {
                getAdapter()?.getData()?.addAll((getAdapter()?.getDataPos(this) ?: 0) + 1, it)
            }
            getAdapter()?.notifyItemChange()
            isShowChild = true
        }
    }
}

open class SelectItem : QuickChildSelectData(layoutId = R.layout.holder_select_item) {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.select, helper = Select2CheckBox::class)
    override var isSelect = false

    @Transient
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnHolder { name + id + "属于:父" + parent?.id }

}