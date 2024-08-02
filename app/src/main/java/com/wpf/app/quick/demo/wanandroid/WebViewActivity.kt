package com.wpf.app.quick.demo.wanandroid

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quickutil.ability.ex.contentViewWithSelf
import com.wpf.app.quickutil.ability.helper.viewGroupCreate
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickwork.ability.helper.title

class WebViewActivity : QuickActivity(
    contentViewWithSelf<WebViewActivity, LinearLayout> {
        title(titleName = self.title ?: "")
        viewGroupCreate(layoutId = R.layout.activity_webview)
    }
) {

    @AutoGet
    var title: String? = null

    @AutoGet
    var url: String? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.webView)
    val webView: WebView? = null

    override fun initView(view: View) {
        super.initView(view)
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