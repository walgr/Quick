package com.wpf.app.quick.demo.http.model

import android.annotation.SuppressLint
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.bindview.QuickRequestData
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickbind.interfaces.runOnContextWithSelf
import com.wpf.app.quickrecyclerview.data.QuickBindData

class 首页文章 : QuickRequestData() {
    val curPage: Int? = null
    val datas: List<文章>? = null

    class 文章 : QuickBindData(layoutId = R.layout.holder_refresh_item) {
        @SuppressLint("NonConstantResourceId")
        @BindData2View(id = R.id.title, helper = Text2TextView::class)
        val title: String? = null
    }

//    @SuppressLint("NonConstantResourceId")
//    @BindData2View(id = R.id.info, helper = Text2TextView::class)
    //自动赋值处理了
    val info = runOnContextWithSelf<String, 首页文章> { _, self ->
        "请求成功：" + self.curPage + "---" + self.datas?.toString()
    }
}