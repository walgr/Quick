package com.wpf.app.quickwidget.group

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import com.wpf.app.quickutil.helper.anim.anim
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickwidget.R

open class QuickSpaceLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(
    context, attrs, defStyleAttr
) {

    private val attr: SpaceLinearLayoutAttr = AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickLinearLayout)

    override fun onFinishInflate() {
        super.onFinishInflate()
        setChildMargin()
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        setChildMargin(false)
    }

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        setChildMargin(false)
    }

    private var curChildCount = 0
    private var curItemSpace = 0
    fun setChildMargin(forceSet: Boolean = false) {
        if (curChildCount == childCount && curItemSpace == attr.itemSpace) return
        curChildCount = childCount
        curItemSpace = attr.itemSpace
        repeat(childCount) { pos ->
            get(pos).updateLayoutParams<MarginLayoutParams> {
                var startSpace = 0
                when (attr.spaceType) {
                    SpaceLinearLayoutAttr.SpaceType.Center.type -> {
                        startSpace = attr.itemSpace / 2
                    }
                    SpaceLinearLayoutAttr.SpaceType.Start.type -> {
                        startSpace = attr.itemSpace
                    }
                    SpaceLinearLayoutAttr.SpaceType.End.type -> {
                        startSpace = 0
                    }
                }
                if (forceSet) {
                    this.marginStart = 0
                    this.marginEnd = 0
                    this.topMargin = 0
                    this.bottomMargin = 0
                }
                if (orientation == HORIZONTAL) {
                    if (childCount == 1) {
                        if (this.marginEnd == 0 || forceSet) {
                            this.marginEnd = attr.itemSpace
                        }
                    } else {
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
                }
                if (orientation == VERTICAL) {
                    if (childCount == 1) {
                        if (this.bottomMargin == 0 || forceSet) {
                            this.bottomMargin = attr.itemSpace
                        }
                    } else {
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
    }

    fun getCurrentSpace() = attr.itemSpace

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

    private class SpaceLinearLayoutAttr(
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

