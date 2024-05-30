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
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickwidget.R

class AutoTagLayout @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    val defStyleAttr: Int = 0,
    private val init: (FlexboxLayoutManager.() -> Unit)? = null,
) : QuickRecyclerView(
    context, attrs, defStyleAttr
) {
    private val attr: AutoTagLayoutAttr by lazy {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.AutoTagLayout)
    }

    override fun initView() {
        layoutManager = QuickFlexboxLayoutManager(context).apply {
            init?.invoke(this) ?: let {
                attrs?.let {
                    attr.tagDirection?.let {
                        this.flexDirection = it
                    }
                    attr.tagWrap?.let {
                        this.flexWrap = it
                    }
                    attr.tagAlignItems?.let {
                        this.alignItems = it
                    }
                    attr.tagAlignContent?.let {
                        this.alignContent = it
                    }
                    attr.tagJustifyContent?.let {
                        this.justifyContent = it
                    }
                    attr.maxLine?.let {
                        this.maxLine = it
                    }
                }
            }
        }
        mQuickAdapter.setRecyclerView(this)
        adapter = mQuickAdapter
    }

    class AutoTagLayoutAttr {

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