package com.wpf.app.quickwork.ability.helper

import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.wpf.app.base.NO_SET
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickutil.helper.wrapMarginLayoutParams

fun ViewGroupScope<out ViewGroup>.img(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    @DrawableRes img: Int = NO_SET,
    clickListener: OnClickListener? = null,
    init: (ImageView.() -> Unit)? = null,
): ImageView {
    val imageView = ImageView(context).apply {
        if (img != NO_SET) {
            setImageResource(img)
        }
        init?.invoke(this)
        clickListener?.let {
            onceClick(onClickListener = it)
        }
    }
    addView(imageView, layoutParams)
    return imageView
}