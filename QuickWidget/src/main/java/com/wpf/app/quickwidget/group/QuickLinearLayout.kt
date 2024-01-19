package com.wpf.app.quickwidget.group

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import com.wpf.app.quickutil.helper.anim
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickwidget.R

open class QuickLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(
    context, attrs, defStyleAttr
) {

    private val attr: QuickLinearLayoutAttr

    init {
        attr = AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickLinearLayout)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setChildMargin()
    }

    private fun setChildMargin(forceSet: Boolean = false) {
        repeat(childCount) { pos ->
            get(pos).updateLayoutParams<MarginLayoutParams> {
                var startSpace = 0
                when (attr.spaceType) {
                    QuickLinearLayoutAttr.SpaceType.Center.type -> {
                        startSpace = attr.itemSpace / 2
                    }
                    QuickLinearLayoutAttr.SpaceType.Start.type -> {
                        startSpace = attr.itemSpace
                    }
                    QuickLinearLayoutAttr.SpaceType.End.type -> {
                        startSpace = 0
                    }
                }
                if (orientation == HORIZONTAL) {
                    if (pos > 0) {
                        if (this.marginStart == 0 || forceSet) {
                            this.marginStart = startSpace
                        }
                    }
                    if (pos < childCount - 1) {
                        if (this.marginEnd == 0 || forceSet) {
                            this.marginEnd = attr.itemSpace - startSpace
                        }
                    }
                }
                if (orientation == VERTICAL) {
                    if (pos > 0) {
                        if (this.topMargin == 0 || forceSet) {
                            this.topMargin = startSpace
                        }
                    }
                    if (pos < childCount - 1) {
                        if (this.bottomMargin == 0 || forceSet) {
                            this.bottomMargin = attr.itemSpace - startSpace
                        }
                    }
                }
            }
        }
    }

    fun setNewItemSpace(itemSpace: Int) {
        attr.itemSpace = itemSpace
        setChildMargin(true)
    }

    fun setNewItemSpaceWithAnim(itemSpace: Int) {
        if (attr.itemSpace == itemSpace) return
        (attr.itemSpace .. itemSpace).anim {
            attr.itemSpace = it
            setChildMargin(true)
        }
    }

    fun setNewSpaceType(spaceType: Int) {
        attr.spaceType = spaceType
        setChildMargin(true)
    }

    private class QuickLinearLayoutAttr(
        var itemSpace: Int = 0,
        var spaceType: Int = 0
    ) {

        enum class SpaceType(val type: Int) {
            Center(0),
            Start(1),
            End(2)
        }
    }
}

