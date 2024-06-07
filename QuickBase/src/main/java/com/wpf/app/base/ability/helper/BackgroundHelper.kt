package com.wpf.app.base.ability.helper

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.graphics.drawable.StateListDrawableCompat
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.ViewScope
import com.wpf.app.quickutil.helper.createDrawable
import com.wpf.app.quickutil.helper.toColor

interface BackgroundScope : ViewScope<View>

fun createBackgroundScope(view: View) = object : BackgroundScope {
    override val view: View = view
}

fun <T : View> T.background(
    drawable: Drawable? = null,
    builder: (BackgroundScope.() -> Unit)? = null,
): T {
    drawable?.let {
        background = it
    }
    builder?.invoke(createBackgroundScope(this))
    return this
}

@Suppress("unused")
fun BackgroundScope.backgroundRes(
    @DrawableRes drawableRes: Int,
): View {
    view.setBackgroundResource(drawableRes)
    return view
}

fun ViewGroupScope<out ViewGroup>.background(
    shape: Int = GradientDrawable.RECTANGLE,
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = 0f,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    return view.background {
        background(
            shape,
            color,
            colorStateList,
            orientation,
            centerX,
            centerY,
            gradientColors,
            radius,
            topLeftRadius,
            topRightRadius,
            bottomRightRadius,
            bottomLeftRadius,
            borderColor,
            borderWidth
        )
    }
}

fun BackgroundScope.background(
    shape: Int = GradientDrawable.RECTANGLE,
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = 0f,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    view.background = createDrawable(
        shape,
        color,
        colorStateList,
        orientation,
        centerX,
        centerY,
        gradientColors,
        radius,
        topLeftRadius,
        topRightRadius,
        bottomRightRadius,
        bottomLeftRadius,
        borderColor,
        borderWidth
    )
    return view
}

fun ViewGroupScope<out ViewGroup>.rect(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = 0f,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    return view.background {
        rect(
            color,
            colorStateList,
            orientation,
            centerX,
            centerY,
            gradientColors,
            radius,
            topLeftRadius,
            topRightRadius,
            bottomRightRadius,
            bottomLeftRadius,
            borderColor,
            borderWidth
        )
    }
}

fun BackgroundScope.rect(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = 0f,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    return background(
        shape = GradientDrawable.RECTANGLE,
        color = color,
        colorStateList = colorStateList,
        orientation = orientation,
        centerX = centerX,
        centerY = centerY,
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

@Suppress("unused")
fun ViewGroupScope<out ViewGroup>.oval(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = 0f,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    return view.background {
        oval(
            color,
            colorStateList,
            orientation,
            centerX,
            centerY,
            gradientColors,
            radius,
            topLeftRadius,
            topRightRadius,
            bottomRightRadius,
            bottomLeftRadius,
            borderColor,
            borderWidth
        )
    }
}

fun BackgroundScope.oval(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    radius: Float = 0f,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    return background(
        shape = GradientDrawable.OVAL,
        color = color,
        colorStateList = colorStateList,
        orientation = orientation,
        centerX = centerX,
        centerY = centerY,
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

@Suppress("unused")
fun ViewGroupScope<out ViewGroup>.ring(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    return view.background {
        ring(
            color,
            colorStateList,
            orientation,
            centerX,
            centerY,
            gradientColors,
            borderColor,
            borderWidth
        )
    }
}

fun BackgroundScope.ring(
    @ColorInt color: Int = android.R.color.white.toColor(),
    colorStateList: ColorStateList? = null,
    orientation: Orientation = Orientation.LEFT_RIGHT,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    @ColorInt gradientColors: IntArray? = null,
    @ColorInt borderColor: Int = 0,
    borderWidth: Int = 0,        //单位px
): View {
    return background(
        shape = GradientDrawable.RING,
        color = color,
        colorStateList = colorStateList,
        orientation = orientation,
        centerX = centerX,
        centerY = centerY,
        gradientColors = gradientColors,
        borderColor = borderColor,
        borderWidth = borderWidth,
    )
}

fun BackgroundScope.state(
    defaultDrawable: Drawable,
    selectedDrawable: Drawable? = null,
    checkedDrawable: Drawable? = null,
    enabledDrawable: Drawable? = null,
): View {
    val stateDrawable = StateListDrawableCompat().apply {
        selectedDrawable?.let {
            addState(intArrayOf(android.R.attr.state_selected), it)
        }
        checkedDrawable?.let {
            addState(intArrayOf(android.R.attr.state_checkable), it)
        }
        enabledDrawable?.let {
            addState(intArrayOf(android.R.attr.state_enabled), it)
        }
        addState(intArrayOf(), defaultDrawable)
    }
    view.background = stateDrawable
    return view
}

fun ViewGroupScope<out ViewGroup>.state(
    defaultDrawable: Drawable,
    selectedDrawable: Drawable? = null,
    checkedDrawable: Drawable? = null,
    enabledDrawable: Drawable? = null,
): View {
    return view.background {
        state(defaultDrawable, selectedDrawable, checkedDrawable, enabledDrawable)
    }
}