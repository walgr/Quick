package com.wpf.app.quick.demo.wanandroid.model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wpf.app.quick.demo.wanandroid.WebViewActivity
import com.wpf.app.quickrecyclerview.data.QuickComposeData
import com.wpf.app.quickrecyclerview.data.runOnCompose
import com.wpf.app.quickutil.quickStartActivity

@OptIn(InternalComposeApi::class)
open class Article : QuickComposeData<Article>(onCompose = runOnCompose { self ->
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .padding(16.dp)
            .clickable {
                self
                    .getContext()
                    ?.quickStartActivity(
                        WebViewActivity::class.java,
                        mapOf("url" to self.link, "title" to self.title)
                    )
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(text = self.title ?: "", modifier = Modifier.fillMaxWidth())
    }
}, autoSet = true) {
    val id: String? = null
    val title: String? = null
    val link: String? = null

//    override fun onClick() {
//        getContext()?.startActivity(WebViewActivity::class.java, mapOf("url" to link, "title" to title))
//    }
}