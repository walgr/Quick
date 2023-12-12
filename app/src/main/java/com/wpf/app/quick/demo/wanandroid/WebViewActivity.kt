package com.wpf.app.quick.demo.wanandroid

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.annotations.AutoGet

class WebViewActivity : QuickActivity(R.layout.activity_webview) {

    @AutoGet
    var title: String? = null

    @AutoGet
    var url: String? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.webView)
    val webView: WebView? = null

    override fun initView() {
        supportActionBar?.title = title
        webView?.let {
            it.webChromeClient = WebChromeClient()
            it.webViewClient = WebViewClient()
            it.loadUrl(url!!)
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView?.canGoBack() == true) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }
}