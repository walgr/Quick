package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.helper.binddatahelper.Select2CheckBox
import com.wpf.app.quick.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.utils.LogUtil
import com.wpf.app.quick.widgets.selectview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.selectview.data.QuickParentSelectData
import com.wpf.app.quickbind.interfaces.runOnHolder

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class ParentSelectItem : QuickParentSelectData(layoutId = R.layout.holder_select_parent_item, canCancel = false) {

    @Transient
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnHolder { "" + name + id + getParentName() + "(${getChildSelectSize()})"}

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