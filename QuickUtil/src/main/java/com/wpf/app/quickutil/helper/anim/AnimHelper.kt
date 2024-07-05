package com.wpf.app.quickutil.helper.anim

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

fun ClosedFloatingPointRange<Float>.anim(
    duration: Long = 200,
    interpolator: Interpolator = LinearInterpolator(),
    repeatCount: Int = 0,
    repeatMode: Int = ValueAnimator.RESTART,
    listener: Animator.AnimatorListener? = null,
    function: (Float) -> Unit,
): Animator {
    val animator = ValueAnimator.ofFloat(this.start, this.endInclusive).apply {
        this.duration = duration
        this.interpolator = interpolator
        this.repeatCount = repeatCount
        this.repeatMode = repeatMode
        addUpdateListener {
            function.invoke(it.animatedValue as Float)
        }
        listener?.let {
            addListener(listener)
        }
    }
    animator.start()
    return animator
}

fun ClosedRange<Int>.anim(
    duration: Long = 200,
    interpolator: Interpolator = LinearInterpolator(),
    repeatCount: Int = 0,
    repeatMode: Int = ValueAnimator.RESTART,
    listener: Animator.AnimatorListener? = null,
    function: (Int) -> Unit,
): Animator {
    val animator = ValueAnimator.ofInt(this.start, this.endInclusive).apply {
        this.duration = duration
        this.interpolator = interpolator
        this.repeatCount = repeatCount
        this.repeatMode = repeatMode
        addUpdateListener {
            function.invoke(it.animatedValue as Int)
        }
        listener?.let {
            addListener(listener)
        }
    }
    animator.start()
    return animator
}

fun View.alphaAnim(
    fromAlpha: Float, toAlpha: Float,
    duration: Long = 200,
    interpolator: Interpolator = LinearInterpolator(),
    repeatCount: Int = 0,
    repeatMode: Int = ValueAnimator.RESTART,
    listener: Animation.AnimationListener? = null,
): Animation {
    val animation = AlphaAnimation(fromAlpha, toAlpha)
    animation.duration = duration
    animation.interpolator = interpolator
    animation.repeatCount = repeatCount
    animation.repeatMode = repeatMode
    animation.setAnimationListener(listener)
    startAnimation(animation)
    return animation
}