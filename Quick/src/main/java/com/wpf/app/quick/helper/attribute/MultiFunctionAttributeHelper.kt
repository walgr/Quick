package com.wpf.app.quick.helper.attribute

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.wpf.app.quick.R
import com.wpf.app.quick.constant.*
import com.wpf.app.quick.helper.attribute.base.AutoGetAttributeHelper

/**
 * Created by 王朋飞 on 2022/5/7.
 *
 */
class MultiFunctionAttributeHelper(
    context: Context,
    attributeSet: AttributeSet
) : AutoGetAttributeHelper(context, attributeSet, R.styleable.MultiFunctionView) {

    var showCheckBox: Boolean? = null
    var isCheck: Boolean? = null

    //CheckBox大小
    var checkBoxWidth: Int? = null

    //CheckBox背景
    @DrawableRes
    var checkboxBackground: Int? = null
    var checkboxBackgroundColor: Color? = null

    //CheckBox左右边距
    var checkboxLeftMarge: Int? = 0
    var checkboxRightMarge: Int? = 0

    var title: String? = null
    var titleHint: String? = null
    @DrawableRes
    var titleBackground: Int? = null
    var titleSize: Int? = null
    @ColorRes
    var titleColor: Int? = null
    @ColorRes
    var titleHintColor: Int? = null
    var titleWpfStyle: Int? = null
    var titleMaxLine: Int? = null

    var subTitle: String? = null
    var subTitleHint: String? = null
    @DrawableRes
    var subTitleBackground: Int? = null
    var subTitleSize: Int? = null
    @ColorRes
    var subTitleColor: Int? = null
    @ColorRes
    var subTitleHintColor: Int? = null
    var subTitleWpfStyle: Int? = null
    var subTitleMarginTop: Int? = null
    var subTitleMaxLine: Int? = null

    var showLeftImage: Boolean? = null
    @DrawableRes
    var leftImageBackground: Int? = null
    @DrawableRes
    var leftImageSrc: Int? = null
    var leftImageWidth: Int? = null
        get() = field ?: ViewGroup.LayoutParams.WRAP_CONTENT
    var leftImageScaleType: Int = 3
    var leftImageLeftMarge: Int? = 0
    var leftImageRightMarge: Int? = 0

    var showRightImage: Boolean? = null
    @DrawableRes
    var rightImageBackground: Int? = null
    @DrawableRes
    var rightImageSrc: Int? = null
    var rightImageWidth: Int? = null
        get() = field ?: ViewGroup.LayoutParams.WRAP_CONTENT
    var rightImageScaleType: Int = 3
    var rightImageLeftMarge: Int? = 0
    var rightImageRightMarge: Int? = 0

    var clickView: Int? = null
        get() {
            return when (field) {
                0 -> ItemView
                1 -> CheckBox
                2 -> LeftImage
                3 -> Title
                4 -> SubTitle
                5 -> RightImage
                else -> ItemView
            }
        }

    var clickOnlyCheckBox: Boolean? = null
}