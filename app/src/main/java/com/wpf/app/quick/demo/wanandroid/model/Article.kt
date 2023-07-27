package com.wpf.app.quick.demo.wanandroid.model

import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.wanandroid.WebViewActivity
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickClickData
import com.wpf.app.quickutil.startActivity

open class Article : QuickClickData(layoutId = R.layout.holder_refresh_item, autoSet = true) {
    val id: String? = null
    val title: String? = null
    val link: String? = null

    override fun onClick() {
        getContext()?.startActivity(WebViewActivity::class.java, mapOf("url" to link, "title" to title))
    }
}