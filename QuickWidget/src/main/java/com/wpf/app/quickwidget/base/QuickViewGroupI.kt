package com.wpf.app.quickwidget.base

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
import com.wpf.app.quickutil.helper.children
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.generic.GenericEx
import java.lang.reflect.Constructor

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
        shadowView: ViewGroup?,
        groupType: Int,
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): ViewGroup? {
        if (shadowView != null) return shadowView
        return when (groupType) {
            GroupType.TabLayout.type -> {
                TabLayout(context, attrs, defStyleAttr)
            }

            GroupType.LinearLayout.type -> {
                LinearLayout(context, attrs, defStyleAttr)
            }

            GroupType.RelativeLayout.type -> {
                RelativeLayout(context, attrs, defStyleAttr)
            }

            GroupType.FrameLayout.type -> {
                FrameLayout(context, attrs, defStyleAttr)
            }

            GroupType.ConstraintLayout.type -> {
                ConstraintLayout(context, attrs, defStyleAttr)
            }

            GroupType.RadioGroup.type -> {
                RadioGroup(context, attrs)
            }

            else -> {
                initOtherViewGroupByXml(context, attrs, defStyleAttr)
            }
        }
    }

    fun initOtherViewGroupByXml(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): ViewGroup? {
        return null
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
            val t = tCls.constructors.findLast {
                val parameters = it.parameterTypes
                if (parameters.size == 3 || parameters.size == 2) {
                    (parameters.contains(Context::class.java)
                            && parameters.contains(AttributeSet::class.java)
                            && parameters.contains(Int::class.java))
                            || (parameters.contains(Context::class.java)
                            && parameters.contains(AttributeSet::class.java))
                } else false
            } as? Constructor<T> ?: return null
            val tInstance = (if (t.parameterTypes.size == 3) t.newInstance(
                context,
                attrs,
                defStyleAttr
            ) else t.newInstance(context, attrs)).apply {
                layoutParams = matchMarginLayoutParams()
            }
            return tInstance as T
        }
        return null
    }

    fun addChildToT(shadowView: ViewGroup?, curView: ViewGroup) {
        curView.children().forEach {
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
        shadowView?.setPadding(
            curView.paddingLeft,
            curView.paddingTop,
            curView.paddingRight,
            curView.paddingBottom
        )
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