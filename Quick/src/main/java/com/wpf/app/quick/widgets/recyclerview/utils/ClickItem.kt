package com.wpf.app.quick.widgets.recyclerview.utils

import android.view.View
import com.wpf.app.quick.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quick.helper.binddatahelper.ItemClick
import com.wpf.app.quick.widgets.recyclerview.QuickBindData
import com.wpf.app.quickbind.interfaces.RunItemClick
import com.wpf.app.quickbind.interfaces.RunItemClickWithSelf

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
open class ClickItem @JvmOverloads constructor(
    override val layoutId: Int,
    val clickSelf: RunItemClickWithSelf<ClickItem>? = null,
    private val click: RunItemClick? = null,
) : QuickBindData(layoutId) {

    override fun onCreateViewHolder(itemView: View) {
        super.onCreateViewHolder(itemView)
        if (clickSelf != null || click != null) {
            BindData2ViewHelper.bind(itemView, clickSelf?.run(this) ?: click?.run()!!, ItemClick)
        }
    }
}