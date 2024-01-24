package com.wpf.app.quickwidget.list.tag

import android.content.Context
import android.util.AttributeSet
import com.google.android.flexbox.AlignContent
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickutil.helper.attribute.AutoGetAttribute
import com.wpf.app.quickwidget.R

class AutoTagLayout @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    val defStyleAttr: Int = 0,
    private val init: (FlexboxLayoutManager.() -> Unit)? = null
) : QuickRecyclerView(
    context, attrs, defStyleAttr
) {

    override fun initView() {
        layoutManager = FlexboxLayoutManager(context).apply {
            init?.invoke(this) ?: let {
                attrs?.let {
                    val autoTagAttrs = AutoTagLayoutAttr(context, attrs)
                    autoTagAttrs.tagDirection?.let {
                        this.flexDirection = it
                    }
                    autoTagAttrs.tagWrap?.let {
                        this.flexWrap = it
                    }
                    autoTagAttrs.tagAlignItems?.let {
                        this.alignItems = it
                    }
                    autoTagAttrs.tagAlignContent?.let {
                        this.alignContent = it
                    }
                    autoTagAttrs.tagJustifyContent?.let {
                        this.justifyContent = it
                    }
                    autoTagAttrs.maxLine?.let {
                        this.maxLine = it
                    }
                }
            }
        }
        mQuickAdapter.mRecyclerView = this
        adapter = mQuickAdapter
    }


    private class AutoTagLayoutAttr(
        context: Context,
        attrs: AttributeSet? = null,
    ) : AutoGetAttribute(context, attrs, R.styleable.AutoTagLayout) {

        @FlexDirection
        val tagDirection: Int? = null

        @FlexWrap
        val tagWrap: Int? = null

        @AlignItems
        val tagAlignItems: Int? = null

        @AlignContent
        val tagAlignContent: Int? = null

        @JustifyContent
        val tagJustifyContent: Int? = null

        val maxLine: Int? = null
    }
}