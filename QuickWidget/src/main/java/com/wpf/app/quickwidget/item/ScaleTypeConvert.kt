package com.wpf.app.quickwidget.item

import android.widget.ImageView.ScaleType

/**
 * Created by 王朋飞 on 2022/5/9.
 *
 */
object ScaleTypeConvert {

    private val sScaleTypeArray = arrayOf(
        ScaleType.MATRIX,
        ScaleType.FIT_XY,
        ScaleType.FIT_START,
        ScaleType.FIT_CENTER,
        ScaleType.FIT_END,
        ScaleType.CENTER,
        ScaleType.CENTER_CROP,
        ScaleType.CENTER_INSIDE
    )

    fun toAndroidScaleType(scaleType: Int): ScaleType {
        return sScaleTypeArray[scaleType]
    }
}