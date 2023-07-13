package com.wpf.app.r2test

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickrecyclerview.data.QuickClickData
import com.wpf.app.quickbind.helper.binddatahelper.Url2ImageView
import com.wpf.app.quickbind.interfaces.runOnContext
import com.wpf.app.quickbind.interfaces.runOnHolder
import com.wpf.app.quickutil.LogUtil

/**
 * Created by 王朋飞 on 2022/7/5.
 */
class BindDataTestModel : QuickClickData(layoutViewInContext = runOnContext {
    ImageView(it).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        id = R2.id.info
    }
}) {
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R2.id.info, helper = Url2ImageView::class)
    var img =
        "https://img1.baidu.com/it/u=3009731526,373851691&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500"

    val title = runOnHolder {
        "Title:${getViewHolder()?.bindingAdapterPosition}"
    }

    override fun onClick() {
        LogUtil.e("点击了第${getViewPos() + 1}个")
    }
}