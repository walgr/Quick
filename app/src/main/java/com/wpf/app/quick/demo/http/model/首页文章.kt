package com.wpf.app.quick.demo.http.model

import android.annotation.SuppressLint
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickrecyclerview.data.QuickBindData

class 首页文章 {
    val curPage: Int? = null
    val datas: List<文章>? = null

    class 文章: QuickBindData(layoutId = R.layout.holder_refresh_item) {
        @SuppressLint("NonConstantResourceId")
        @BindData2View(id = R.id.title, helper = Text2TextView::class)
        val title: String?= null
    }
}