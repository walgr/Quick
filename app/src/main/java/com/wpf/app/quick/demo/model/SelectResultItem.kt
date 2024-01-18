package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickwidget.selectview.data.QuickChildSelectData
import com.wpf.app.quickwidget.selectview.data.QuickParentSelectData
import com.wpf.app.quickutil.bind.itemClickRun
import com.wpf.app.quickutil.bind.itemClickWithSelf

/**
 * Created by 王朋飞 on 2022/9/16.
 *
 */
class SelectResultItem(
    override var isSelect: Boolean,
    override var parent: QuickParentSelectData? = null,
    override var id: String?,
    override var name: String?,
) : QuickChildSelectData(
    layoutId = R.layout.holder_select_result_item,
    onChildClick = itemClickWithSelf { self ->
        itemClickRun {
            self.isSelect = false
            self.getAdapter()?.getData()?.remove(self)
            self.getAdapter()?.notifyItemRemoved(self.getDataPos())
            self.getAdapter()?.getOnSelectChangeListener()?.onSelectChange()
        }
    }) {
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    val title = name + id + "属于：" + parent?.name + parent?.id
}