package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickbind.helper.binddatahelper.Select2CheckBox
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.widgets.quickview.QuickSelectView
import com.wpf.app.quickbind.interfaces.itemClick
import com.wpf.app.quickbind.interfaces.runOnHolder

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectItemView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickSelectView(mContext, attributeSet, defStyleAttr, layoutId = R.layout.holder_select_item) {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.select, helper = Select2CheckBox::class)
    override var isSelect = false

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnHolder { "Data2View$position" }

    @BindData2View(helper = ItemClick::class)
    var itemClick = itemClick {
        isSelect = !isSelect
        BindData2ViewHelper.bind(getView()?.findViewById(R.id.select)!!, isSelect, Select2CheckBox)
        Toast.makeText(
            it.context,
            "点击" + title.run(it),
            Toast.LENGTH_SHORT
        ).show()
    }
}