package com.wpf.app.base.ability.helper

import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes
import com.wpf.app.base.NO_SET
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickutil.helper.wrapMarginLayoutParams

@Suppress("unused")
fun ContextScope.img(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    @DrawableRes img: Int = NO_SET,
    scaleType: ScaleType = ScaleType.FIT_CENTER,
    clickListener: OnClickListener? = null,
    init: (ImageView.() -> Unit)? = null,
): ImageView {
    val imageView = ImageView(context).apply {
        if (img != NO_SET) {
            setImageResource(img)
        }
        this.scaleType = scaleType
        init?.invoke(this)
        clickListener?.let {
            onceClick(onClickListener = it)
        }
    }
    addView(imageView, layoutParams)
    return imageView
}