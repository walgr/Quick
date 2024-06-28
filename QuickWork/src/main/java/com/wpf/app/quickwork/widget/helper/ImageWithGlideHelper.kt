package com.wpf.app.quickwork.widget.helper

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun ImageView.loadUrl(
    imgUrl: String,
    glideBuilder: (RequestBuilder<Drawable>.() -> Unit)? = null,
): ImageView {
    val gBuilder = Glide.with(this)
        .load(imgUrl)
    glideBuilder?.invoke(gBuilder)
    gBuilder.into(this)
    return this
}

fun ImageView.loadUrlAsync(
    returnUrl: suspend () -> String,
    glideBuilder: (RequestBuilder<Drawable>.() -> Unit)? = null,
): ImageView {
    CoroutineScope(Dispatchers.IO).launch {
        val imgUrl = async { returnUrl() }.await()
        loadUrl(imgUrl = imgUrl, glideBuilder)
    }
    return this
}