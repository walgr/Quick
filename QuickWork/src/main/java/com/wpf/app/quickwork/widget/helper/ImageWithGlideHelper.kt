package com.wpf.app.quickwork.widget.helper

import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun ImageView.loadUrl(imgUrl: String): ImageView {
    val glideBuilder = Glide.with(this)
        .load(imgUrl)
    glideBuilder.into(this)
    return this
}

fun ImageView.loadUrlAsync(returnUrl: suspend () -> String): ImageView {
    CoroutineScope(Dispatchers.IO).launch {
        val imgUrl = async { returnUrl() }.await()
        loadUrl(imgUrl = imgUrl)
    }
    return this
}