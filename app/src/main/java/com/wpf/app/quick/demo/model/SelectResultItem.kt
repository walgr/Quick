package com.wpf.app.quick.demo.model

import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.widgets.recyclerview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.recyclerview.data.QuickParentSelectData
import com.wpf.app.quickbind.interfaces.itemClickRun
import com.wpf.app.quickbind.interfaces.itemClickWithSelf

/**
 * Created by 王朋飞 on 2022/9/16.
 *
 */
class SelectResultItem(
    override var isSelect: Boolean,
    override var parent: QuickParentSelectData? = null,
    override var id: String?,
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    override var name: String?
) : QuickChildSelectData(
    layoutId = R.layout.holder_select_result_item,
    onChildClick = itemClickWithSelf { self ->
        itemClickRun {
            self.isSelect = false
            val index = self.getAdapter().getData()?.indexOf(self) ?: 0
            self.getAdapter().getData()?.remove(self)
            self.getAdapter().notifyItemRemoved(index)
            self.getAdapter().getOnSelectChangeListener()?.onSelectChange()
        }
    })