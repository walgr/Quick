package com.wpf.app.quick.demo.wanandroid

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.myLayout
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickutil.helper.warpContentHeightParams
import com.wpf.app.quickwork.ability.title
import com.wpf.app.quickwork.widget.QuickTitleView

class WebViewActivity : QuickAbilityActivity(
    contentView<LinearLayout> {
        title()
        myLayout(R.layout.activity_webview)
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
        view.findViewById<QuickTitleView>(R.id.titleView).setTitle(title)
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