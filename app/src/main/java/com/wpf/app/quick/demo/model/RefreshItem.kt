package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import android.widget.Toast
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.helper.binddatahelper.ItemClick
import com.wpf.app.quick.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.widgets.recyclerview.QuickBindData
import com.wpf.app.quickbind.interfaces.itemClick
import com.wpf.app.quickbind.interfaces.runOnHolder

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class RefreshItem : QuickBindData(R.layout.holder_refresh_item) {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = runOnHolder {
        "Title " + getViewHolder()?.bindingAdapterPosition
    }

    @BindData2View(helper = ItemClick::class)
    var itemClick = itemClick {
        Toast.makeText(
            it.context,
            "点击" + getViewHolder()?.bindingAdapterPosition,
            Toast.LENGTH_SHORT
        ).show()
    }
}