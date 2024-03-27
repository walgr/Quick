package com.wpf.app.quickwork.ability

import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickwork.R
import com.wpf.app.quickwork.widget.QuickTitleView

fun QuickTitleView.textButton(
    text: CharSequence,
    clickListener: OnClickListener? = null,
    init: (TextView.() -> Unit)? = null,
): TextView {
    val textView = TextView(context).apply {
        this.text = text
        dealChildViewCommonStyle(this)
        init?.invoke(this)
        clickListener?.let {
            onceClick(onClickListener = it)
        }
    }
    addView(textView)
    return textView
}

fun QuickTitleView.imgButton(
    @DrawableRes img: Int,
    clickListener: OnClickListener? = null,
    init: (ImageView.() -> Unit)? = null,
) {
    addView(ImageView(context).apply {
        setImageResource(img)
        dealChildViewCommonStyle(this)
        init?.invoke(this)
        clickListener?.let {
            onceClick(onClickListener = it)
        }
    })
}

fun ViewGroup.title(
    titleName: String = "",
    showTitle: Boolean = true,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams,
    builder: (QuickTitleView.() -> Unit)? = null,
): QuickTitleView {
    val titleView = QuickTitleView(context)
    if (!showTitle) return titleView
    titleView.id = R.id.quickTitleView
    titleView.setTitle(titleName)
    addView(titleView, layoutParams)
    builder?.invoke(titleView)
    return titleView
}