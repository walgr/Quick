package com.wpf.app.quickutil.helper

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import com.wpf.app.quickutil.other.forceTo

fun createDrawable(
    shape: Int = GradientDrawable.RECTANGLE,
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = 0f,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,               //单位px
): Drawable {
    return GradientDrawable().mutate().forceTo<GradientDrawable>().apply {
        this.shape = shape

        colorStateList?.let {
            setColor(it)
        } ?: setColor(color)

        gradientColors?.let {
            setOrientation(orientation)
            this.setGradientCenter(centerX, centerY)
            colors = it
        }

        borderColor.takeIf { it != 0 }?.let {
            setStroke(borderWidth, it)
        }

        if (topLeftRadius != 0f || topRightRadius != 0f || bottomRightRadius != 0f || bottomLeftRadius != 0f) {
            cornerRadii = floatArrayOf(
                topLeftRadius,
                topLeftRadius,
                topRightRadius,
                topRightRadius,
                bottomRightRadius,
                bottomRightRadius,
                bottomLeftRadius,
                bottomLeftRadius
            )
        } else {
            radius.takeIf { it != 0f }?.let {
                setCornerRadius(it)
            }
        }
    }
}