package com.wpf.app.base.helper

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.wpf.app.base.R

/**
 * Created by 王朋飞 on 2022/5/7.
 *
 */
class MultiFunctionAttributeHelper
    (context: Context,
     attributeSet: AttributeSet
): AttributeListHelper(context, attributeSet, R.styleable.MultiFunctionView) {

    var showCheckBox: Boolean? = null
    var isChecked: Boolean? = null
    //CheckBox大小
    var checkBoxWidth: Int? = null
    //CheckBox背景
    @DrawableRes var checkboxBackground: Int? = null
    var checkboxBackgroundColor: Color? = null
    //CheckBox左右边距
    var checkboxLeftMarge: Int? = 0
    var checkboxRightMarge: Int? = 0

    var title: String? = null
    @DrawableRes var titleBackground: Int? = null
    var titleSize: Int? = null
    @ColorRes var titleColor: Int? = null
    var titleStyle: Int? = null
    var titleMaxLine: Int? = null

    var subTitle: String? = null
    @DrawableRes var subTitleBackground: Int? = null
    var subTitleSize: Int? = null
    @ColorRes var subTitleColor: Int? = null
    var subTitleStyle: Int? = null
    var subTitleMarginTop: Int? = null
    var subTitleMaxLine: Int? = null

    var showLeftImage: Boolean? = null
    @DrawableRes var leftImageBackground: Int? = null
    @DrawableRes var leftImageSrc: Int? = null
    var leftImageWidth: Int? = null
    var leftImageScaleType: Int = 3
    var leftImageLeftMarge: Int? = 0
    var leftImageRightMarge: Int? = 0

    var showRightImage: Boolean? = null
    @DrawableRes var rightImageBackground: Int? = null
    @DrawableRes var rightImageSrc: Int? = null
    var rightImageWidth: Int? = null
    var rightImageScaleType: Int = 3
    var rightImageLeftMarge: Int? = 0
    var rightImageRightMarge: Int? = 0
}