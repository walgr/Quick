package com.wpf.app.quick.ability.helper

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.wpf.app.base.NO_SET
import com.wpf.app.base.NO_SET_F
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.other.forceTo

class ViewBackgroundScope(
    val view: View,
)

fun View.background(drawable: Drawable? = null, builder: (ViewBackgroundScope.() -> Unit)? = null): View {
    drawable?.let {
        background = it
    }
    builder?.invoke(ViewBackgroundScope(this))
    return this
}

fun ViewBackgroundScope.backgroundRes(
    @DrawableRes drawableRes: Int,
): View {
    view.setBackgroundResource(drawableRes)
    return view
}

fun ViewBackgroundScope.background(
    shape: Int = GradientDrawable.RECTANGLE,
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = NO_SET_F,
    topLeftRadius: Float = NO_SET_F,
    topRightRadius: Float = NO_SET_F,
    bottomRightRadius: Float = NO_SET_F,
    bottomLeftRadius: Float = NO_SET_F,
    @ColorInt borderColor: Int = NO_SET,
    borderWidth: Int = 0,        //单位px
): View {
    view.background = GradientDrawable().mutate().forceTo<GradientDrawable>().apply {
        this.shape = shape

        colorStateList?.let {
            setColor(it)
        } ?: setColor(color)

        gradientColors?.let {
            setOrientation(orientation)
            colors = it
        }

        borderColor.takeIf { it != NO_SET }?.let {
            setStroke(borderWidth, it)
        }

        if (topLeftRadius != NO_SET_F || topRightRadius != NO_SET_F || bottomRightRadius != NO_SET_F || bottomLeftRadius != NO_SET_F) {
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
            radius.takeIf { it != NO_SET_F }?.let {
                setCornerRadius(it)
            }
        }
    }
    return view
}

fun ViewBackgroundScope.rect(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = NO_SET_F,
    topLeftRadius: Float = NO_SET_F,
    topRightRadius: Float = NO_SET_F,
    bottomRightRadius: Float = NO_SET_F,
    bottomLeftRadius: Float = NO_SET_F,
    @ColorInt borderColor: Int = NO_SET,
    borderWidth: Int = 0,        //单位px
): View {
    return background(
        shape = GradientDrawable.RECTANGLE,
        color = color,
        colorStateList = colorStateList,
        orientation = orientation,
        gradientColors = gradientColors,
        radius = radius,
        topLeftRadius = topLeftRadius,
        topRightRadius = topRightRadius,
        bottomRightRadius = bottomRightRadius,
        bottomLeftRadius = bottomLeftRadius,
        borderColor = borderColor,
        borderWidth = borderWidth,
    )
}

fun ViewBackgroundScope.oval(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = NO_SET_F,
    topLeftRadius: Float = NO_SET_F,
    topRightRadius: Float = NO_SET_F,
    bottomRightRadius: Float = NO_SET_F,
    bottomLeftRadius: Float = NO_SET_F,
    @ColorInt borderColor: Int = NO_SET,
    borderWidth: Int = 0,        //单位px
): View {
    return background(
        shape = GradientDrawable.OVAL,
        color = color,
        colorStateList = colorStateList,
        orientation = orientation,
        gradientColors = gradientColors,
        radius = radius,
        topLeftRadius = topLeftRadius,
        topRightRadius = topRightRadius,
        bottomRightRadius = bottomRightRadius,
        bottomLeftRadius = bottomLeftRadius,
        borderColor = borderColor,
        borderWidth = borderWidth,
    )
}