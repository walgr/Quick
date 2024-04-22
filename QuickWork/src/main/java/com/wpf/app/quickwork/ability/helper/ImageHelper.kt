package com.wpf.app.quickwork.ability.helper

import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickutil.helper.wrapLayoutParams

fun ViewGroupScope<out ViewGroup>.imgButton(
    layoutParams: LayoutParams = wrapLayoutParams(),
    @DrawableRes img: Int,
    clickListener: OnClickListener? = null,
    init: (ImageView.() -> Unit)? = null,
): ImageView {
    val imageView = ImageView(context).apply {
        setImageResource(img)
        init?.invoke(this)
        clickListener?.let {
            onceClick(onClickListener = it)
        }
    }
    addView(imageView, layoutParams)
    return imageView
}