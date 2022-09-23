package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quick.R
import com.wpf.app.quick.helper.attribute.base.AutoGetAttributeHelper

/**
 * Created by 王朋飞 on 2022/9/23.
 *
 */
class QuickViewGroupAttrSetHelper(
    mContext: Context,
    attributeSet: AttributeSet,
) : AutoGetAttributeHelper(mContext, attributeSet, R.styleable.QuickViewGroup) {

    val groupType: Int? = null
}

enum class GroupType(type: Int) {
    LinearLayout(0),
    RelativeLayout(1),
    FrameLayout(2),
    ConstraintLayout(3),
    RadioGroup(4),
}