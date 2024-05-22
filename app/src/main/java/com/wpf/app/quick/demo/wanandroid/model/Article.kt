package com.wpf.app.quick.demo.wanandroid.model

import android.view.View
import androidx.core.os.bundleOf
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.wanandroid.WebViewActivity
import com.wpf.app.quick.helper.startActivity
import com.wpf.app.quickrecyclerview.data.QuickClickData

open class Article : QuickClickData(R.layout.holder_refresh_item, autoSet = true) {
    val id: String? = null
    val title: String? = null
    val link: String? = null

    override fun onClick(view: View) {
        getContext()?.startActivity(WebViewActivity::class.java, bundleOf("url" to link, "title" to title))
    }
}