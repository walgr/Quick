package com.wpf.app.quick.demo.wanandroid

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickwork.activity.QuickTitleActivity

class WebViewActivity : QuickTitleActivity(contentBuilder = {
    addView(R.layout.activity_webview.toView(context), matchLayoutParams)
}) {

    @AutoGet
    var title: String? = null

    @AutoGet
    var url: String? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.webView)
    val webView: WebView? = null

    override fun initView(view: View) {
        super.initView(view)
        titleView.setTitle(title)
        webView?.let {
            it.webChromeClient = WebChromeClient()
            it.webViewClient = WebViewClient()
            it.loadUrl(url!!)
        }

        onBackPress {
            if (webView?.canGoBack() == true) {
                webView.goBack()
            } else {
                finish()
            }
        }
    }
}