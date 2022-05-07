package cn.goodjobs.client.helper

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import cn.goodjobs.client.R
import cn.goodjobs.client.constant.*

/**
 * Created by 王朋飞 on 2022/5/7.
 *
 */
class MultiFunctionAttributeHelper
    (context: Context,
     attributeSet: AttributeSet
): AttributeListHelper(context, attributeSet, R.styleable.MultiFunctionView) {

    var showCheckBox: Boolean? = false
    var isChecked: Boolean? = false
    //CheckBox大小
    var checkBoxWidth: Int? = 0
    //CheckBox背景
    @DrawableRes var checkboxBackground: Int? = null
    //CheckBox左右边距
    var checkboxLeftMarge: Int? = 0
    var checkboxRightMarge: Int? = 0

    var title: String? = null
    var titleSize: Int? = null
    @ColorRes var titleColor: Int? = null
    var titleStyle: Int? = null

    var subTitle: String? = null
    var subTitleSize: Int? = null
    @ColorRes var subTitleColor: Int? = null
    var subTitleStyle: Int? = null
    var subTitleMarginTop: Int? = null

    var showLeftImage: Boolean? = false
    @DrawableRes var leftImageSrc: Int? = null
    var leftImageWidth: Int? = 0
    var leftImageScaleType: Int = FIT_CENTER
    get() {
        return when (field) {
            0 -> MATRIX
            1 -> FIT_XY
            2 -> FIT_START
            3 -> FIT_CENTER
            4 -> FIT_END
            5 -> CENTER
            6 -> CENTER_CROP
            7 -> CENTER_INSIDE
            8 -> CIRCLE_CROP
            else -> FIT_CENTER
        }
    }
    var leftImageLeftMarge: Int? = 0
    var leftImageRightMarge: Int? = 0

    var showRightImage: Boolean? = false
    @DrawableRes var rightImageSrc: Int? = null
    var rightImageWidth: Int? = 0
    var rightImageScaleType: Int = FIT_CENTER
        get() {
            return when (field) {
                0 -> MATRIX
                1 -> FIT_XY
                2 -> FIT_START
                3 -> FIT_CENTER
                4 -> FIT_END
                5 -> CENTER
                6 -> CENTER_CROP
                7 -> CENTER_INSIDE
                8 -> CIRCLE_CROP
                else -> FIT_CENTER
            }
        }
    var rightImageLeftMarge: Int? = 0
    var rightImageRightMarge: Int? = 0
}