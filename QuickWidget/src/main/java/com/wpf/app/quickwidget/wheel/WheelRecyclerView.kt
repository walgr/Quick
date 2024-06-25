package com.wpf.app.quickwidget.wheel

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import com.wpf.app.quickwidget.selectview.QuickSelectRecyclerView
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class WheelRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): QuickSelectRecyclerView(
    context, attrs, defStyleAttr
) {
    private val mCamera = Camera()
    private val mMatrix = Matrix()
    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        if (canvas == null || child == null) return super.drawChild(canvas, child, drawingTime)
        val centerY = getVerticalSpace() / 2
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

        canvas.save()
        mCamera.save()
        mCamera.translate(0f, 0f, offsetZ)
        mCamera.rotateX(rotateDeg)
        mCamera.getMatrix(mMatrix)
        mCamera.restore()
        mMatrix.preTranslate((-child.width / 2).toFloat(), -childCenterY.toFloat())
        mMatrix.postTranslate((child.width / 2).toFloat(), childCenterY.toFloat())
        canvas.concat(mMatrix)
        val result = super.drawChild(canvas, child, drawingTime)
        canvas.restore()
        return result
    }

    private fun getVerticalSpace(): Int {
        return height - paddingBottom - paddingTop
    }
}