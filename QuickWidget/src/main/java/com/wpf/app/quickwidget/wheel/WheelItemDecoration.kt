package com.wpf.app.quickwidget.wheel

import android.graphics.Canvas
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.wpf.app.quickutil.helper.children
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


class WheelItemDecoration : ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        parent.children().forEach { child ->
            val centerY = getVerticalSpace(parent) / 2
            val childCenterY: Int = child.top + child.height / 2
            val factor = (centerY - childCenterY) * 1f / centerY
            val alphaFactor = (1 - 0.7f * abs(factor.toDouble())).toFloat()
            child.setAlpha(alphaFactor * alphaFactor * alphaFactor)
            val scaleFactor = (1 - 0.3f * abs(factor.toDouble())).toFloat()
            child.scaleX = scaleFactor
            child.scaleY = scaleFactor

            // rotateX calculate rotate radius
            val rotateRadius = 2.0f * centerY / Math.PI.toFloat()
            val rad = (centerY - childCenterY) * 1f / rotateRadius
            val offsetZ = rotateRadius * (1 - cos(rad.toDouble()).toFloat())
            val rotateDeg = rad * 180 / Math.PI.toFloat()
            // offset Y for item rotate
            val offsetY = centerY - childCenterY - rotateRadius * sin(rad.toDouble())
                .toFloat() * 1.3f
            child.translationY = offsetY
            ViewCompat.setZ(child, -offsetZ)
            child.rotationX = rotateDeg
        }
    }

    private fun getVerticalSpace(parent: RecyclerView): Int {
        return parent.height - parent.paddingBottom - parent.paddingTop
    }
}