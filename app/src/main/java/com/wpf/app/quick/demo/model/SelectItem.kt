package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.helper.binddatahelper.ItemClick
import com.wpf.app.quick.helper.binddatahelper.Select2CheckBox
import com.wpf.app.quick.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.widgets.recyclerview.QuickSelectData
import com.wpf.app.quickbind.interfaces.runItemClick
import com.wpf.app.quickbind.interfaces.runOnView

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectItem : QuickSelectData(layoutId = R.layout.holder_select_item) {
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.select, helper = Select2CheckBox::class)
    override var isSelect = false

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title: runOnView<CharSequence> =
        object : runOnView<CharSequence> {
            override fun run(view: View): CharSequence {
                return "Title " + getViewHolder().bindingAdapterPosition
            }
        }

    @BindData2View(helper = ItemClick::class)
    var itemClick: runItemClick = object : runItemClick {
        override fun run(): View.OnClickListener {
            return View.OnClickListener { v: View ->
                isSelect = !isSelect
                Toast.makeText(
                    v.context,
                    "点击" + getViewHolder().bindingAdapterPosition,
                    Toast.LENGTH_SHORT
                ).show()
                getViewHolder().getQuickAdapter()
                    .notifyItemChanged(getViewHolder().bindingAdapterPosition)
            }
        }
    }
}