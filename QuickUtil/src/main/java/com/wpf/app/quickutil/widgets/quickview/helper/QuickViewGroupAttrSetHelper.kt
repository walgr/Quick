package com.wpf.app.quickutil.widgets.quickview.helper

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quickutil.R
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper

/**
 * Created by 王朋飞 on 2022/9/23.
 *
 */
class QuickViewGroupAttrSetHelper(
    mContext: Context,
    attributeSet: AttributeSet,
) : AutoGetAttributeHelper(mContext, attributeSet, R.styleable.QuickViewGroup) {

    val groupType: Int? = null

    val layoutRes: Int? = null
}

enum class GroupType(val type: Int) {
    LinearLayout(0),
    RelativeLayout(1),
    FrameLayout(2),
    ConstraintLayout(3),
    RadioGroup(4),
}