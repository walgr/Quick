package com.wpf.app.quick.demo.wanandroid.model

import com.wpf.app.quick.demo.R
import com.wpf.app.quickrecyclerview.data.QuickBindData

open class Article : QuickBindData(layoutId = R.layout.holder_refresh_item, autoSet = true) {
    val id: String? = null
    val title: String? = null
}