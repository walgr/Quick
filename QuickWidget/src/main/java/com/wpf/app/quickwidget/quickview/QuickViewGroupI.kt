package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.tabs.TabLayout
import com.wpf.app.quickutil.other.GenericEx
import com.wpf.app.quickutil.view.allChild
import com.wpf.app.quickutil.view.matchLayoutParams

enum class GroupType(val type: Int) {
    LinearLayout(0),
    RelativeLayout(1),
    FrameLayout(2),
    ConstraintLayout(3),
    RadioGroup(4),
    TabLayout(5),
}

internal interface QuickViewGroupI<T : ViewGroup> {
    fun initViewGroupByXml(
        shadowView: View?,
        groupType: Int,
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): T? {
        if (shadowView != null) return shadowView as T
        return when (groupType) {
            GroupType.TabLayout.type -> {
                TabLayout(context, attrs, defStyleAttr) as T
            }

            GroupType.LinearLayout.type -> {
                LinearLayout(context, attrs, defStyleAttr) as T
            }

            GroupType.RelativeLayout.type -> {
                RelativeLayout(context, attrs, defStyleAttr) as T
            }

            GroupType.FrameLayout.type -> {
                FrameLayout(context, attrs, defStyleAttr) as T
            }

            GroupType.ConstraintLayout.type -> {
                ConstraintLayout(context, attrs, defStyleAttr) as T
            }

            GroupType.RadioGroup.type -> {
                RadioGroup(context, attrs) as T
            }

            else -> {
                LinearLayout(context, attrs, defStyleAttr) as T
            }
        }
    }

    fun initViewGroupByT(
        shadowView: View?,
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): T? {
        if (shadowView != null) return shadowView as T
        val tCls: Class<T>? = GenericEx.get0Clazz(this)
        if ("ViewGroup" == tCls?.simpleName) return shadowView
        tCls?.let {
            val t =
                tCls.getConstructor(Context::class.java, AttributeSet::class.java, Int::class.java)
            t.newInstance(context, attrs, defStyleAttr).apply {
                layoutParams = matchLayoutParams
            }
            return t as T
        }
        return null
    }

    fun addChildToT(shadowView: ViewGroup?, curView: ViewGroup) {
        curView.allChild().forEach {
            shadowView?.addView(it)
        }
    }

    fun addTToParent(shadowView: View?, curView: ViewGroup) {
        if (shadowView?.parent != null) return
        val parentGroup = curView.parent as? ViewGroup ?: return
        val position = parentGroup.indexOfChild(curView)
        parentGroup.removeView(curView)
        curView.layoutParams?.let {
            shadowView?.layoutParams = it
        }
        shadowView?.setPadding(curView.paddingLeft, curView.paddingTop, curView.paddingRight, curView.paddingBottom)
        parentGroup.addView(shadowView, position)
    }

    fun addTToThis(shadowView: View?, curView: ViewGroup) {
        if (shadowView?.parent != null) return
        shadowView?.let {
            curView.addView(shadowView)
        }
    }

    fun addT(addToParent: Boolean, shadowView: View?, curView: ViewGroup) {
        if (addToParent) {
            addTToParent(shadowView, curView)
        } else {
            addTToThis(shadowView, curView)
        }
    }
}