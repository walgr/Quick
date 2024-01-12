package com.wpf.app.quick.widgets

import android.animation.Animator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DrawFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.core.view.drawToBitmap
import com.wpf.app.quick.R
import com.wpf.app.quick.helper.tab.GroupManager
import com.wpf.app.quickutil.helper.anim
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.emptyPut
import com.wpf.app.quickutil.widgets.quickview.QuickViewGroup
import kotlin.math.max

open class BottomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickViewGroup<LinearLayout>(context, attrs, defStyleAttr, addToParent = false),
    GroupManager.OnGroupChangeListener {

    private var anim: Animator? = null
    private var viewCenterX: Float = 0f
        get() = if (isInEditMode) 40f else field
    private var viewHeight = 0
    private var oldCurView: View? = null
    private var curView: View? = null
    private var viewBitmap: MutableMap<View, Bitmap> = mutableMapOf()
    private var animViewId: Int = 0
    override fun onChange(view: View) {
        if (animViewId == 0) return
        val animView = view.findViewById<View>(animViewId)
        animView.post {
            oldCurView = curView
            curView = animView
            viewBitmap.emptyPut(animView, animView.drawToBitmap())
            circleR = max(animView.width, animView.height).toFloat()
            val location = intArrayOf(0, 0)
            animView.getLocationInWindow(location)
            viewCenterX = location[0] + animView.width / 2f
            viewHeight = animView.height
            contentAppend = 10f
            paddingTopH = 2 * contentR - circleR

            if (anim != null) {
                anim?.cancel()
            }
            val circleCenterX = viewCenterX - allPathWidth / 2f
            if (lastOffsetX == 0f) {
                onAnimProgress(1f)
                //初始时不做动画
                curX = circleCenterX
            } else {
                oldLastOffsetX = lastOffsetX
                anim = (lastOffsetX..circleCenterX).anim(
                    200, interpolator = AccelerateInterpolator()
                ) {
                    onAnimProgress((it - oldLastOffsetX) / (circleCenterX - oldLastOffsetX))
                    curX = it
                }
            }
        }
    }

    private var curAnimProcess: Float = 0f
    private fun onAnimProgress(progress: Float) {
        if (progress < 0 || progress == -0.0f) return
        curAnimProcess = progress
        viewBitmap.keys.forEach {
            if (it != curView) {
                it.alpha = progress
            } else {
                it.alpha = 1 - progress
            }
        }
    }

    private val smallCircleR: Float = 20f
        get() = if (isInEditMode) 20f else field
    private val leftCirclePath by lazy {
        Path().apply {
            val start = 0f
            val end = start + 1 * smallCircleR
            val top = 0f
            val bottom = top + 1 * smallCircleR
            addRect(start, top, end, bottom, Path.Direction.CCW)
            op(
                Path().apply {
                    moveTo(start, top)
                    lineTo(start, bottom)
                    lineTo(end, bottom)
                    lineTo(start, top)
                    close()
                }, Path.Op.DIFFERENCE
            )
            op(
                Path().apply {
                    addArc(start - smallCircleR, top, end + 0, bottom + smallCircleR, -90f, 90f)
                }, Path.Op.DIFFERENCE
            )
        }
    }
    private val circleTopPath by lazy {
        Path().apply {
            val start = smallCircleR
            val end = start + 2 * circleR
            val top = 0f
            val bottom = top + 1 * smallCircleR
            addRect(start, top, end, bottom, Path.Direction.CW)
        }
    }
    private var circleR: Float = 0f
        get() = if (isInEditMode) 50f else field
    private val circlePath by lazy {
        Path().apply {
            val start = smallCircleR
            val end = start + 2 * circleR
            val top = -circleR + smallCircleR
            val bottom = top + 2 * circleR
            addArc(start, top, end, bottom, 0f, 180f)
        }
    }
    private val rightCirclePath by lazy {
        Path().apply {
            val start = 2 * circleR + smallCircleR
            val end = start + 1 * smallCircleR
            val top = 0f
            val bottom = top + 1 * smallCircleR
            addRect(start, top, end, bottom, Path.Direction.CW)
            op(
                Path().apply {
                    addArc(start, top, end + smallCircleR, bottom + smallCircleR, 180f, 270f)
                }, Path.Op.DIFFERENCE
            )
        }
    }

    private val contentR: Float
        get() = circleR - contentAppend
    private var contentAppend: Float = 0f
        get() = if (isInEditMode) 10f else field
    private val contentCirclePath by lazy {
        Path().apply {
            val start = smallCircleR + contentAppend
            val end = start + 2 * contentR
            val top = 0f - contentR + smallCircleR
            val bottom = top + 2 * contentR
            addArc(start, top, end, bottom, 0f, 360f)
        }
    }

    private val allPathWidth: Float
        get() = 2 * (smallCircleR + circleR)

    private var oldLastOffsetX = 0f
    private var lastOffsetX = 0f
    private var paddingTopH = 0f
        get() = if (isInEditMode) 2 * contentR - circleR else field

    private val paint by lazy {
        Paint().apply {
            color = oldColor
            strokeWidth = 10f
        }
    }
    private val path by lazy {
        Path()
    }
    private val drawFilter = DrawFilter()
    private val width by lazy {
        getWidth().toFloat()
    }
    private val height by lazy {
        getHeight().toFloat()
    }
    private val oldColor by lazy {
        background.asTo<ColorDrawable>()?.color ?: Color.TRANSPARENT
    }

    private var curX = 0f
        set(value) {
            field = value
            invalidate()
        }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isInEditMode) {
            dealParentClipChild()
        }
        if (circleR == 0f && !isInEditMode) return
        canvas.drawFilter = drawFilter
        canvas.drawColor(Color.TRANSPARENT)
        val sc = canvas.saveLayer(0f, -paddingTopH, width, height, null)
        drawBgCircle(canvas)
        drawAnimView(canvas)
        lastOffsetX = curX
        canvas.restoreToCount(sc)
    }

    private fun drawBgCircle(canvas: Canvas) {
        path.rewind()
        path.addRect(0f, 0f, width, height, Path.Direction.CW)
        circleTopPath.offset(curX - lastOffsetX, 0f)
        leftCirclePath.offset(curX - lastOffsetX, 0f)
        rightCirclePath.offset(curX - lastOffsetX, 0f)
        circlePath.offset(curX - lastOffsetX, 0f)
        path.op(circleTopPath, Path.Op.DIFFERENCE)
        path.op(leftCirclePath, Path.Op.DIFFERENCE)
        path.op(rightCirclePath, Path.Op.DIFFERENCE)
        path.op(circlePath, Path.Op.DIFFERENCE)
        contentCirclePath.offset(curX - lastOffsetX, 0f)
        path.addPath(contentCirclePath)
        canvas.drawPath(path, paint)
    }

    private fun drawAnimView(canvas: Canvas) {
        viewBitmap[curView]?.let {
            canvas.drawBitmap(
                it,
                curX + it.width / 2 + smallCircleR,
                smallCircleR - it.width / 2,
                paint
            )
        }
//        viewBitmap[oldCurView]?.let {
//            canvas.drawBitmap(
//                it,
//                viewCenterX - allPathWidth / 2f + it.width / 2 + smallCircleR,
//                (smallCircleR - it.width / 2) * (curAnimProcess),
//                paint
//            )
//        }
    }

    private fun dealParentClipChild() {
        parent?.asTo<ViewGroup>()?.clipChildren = false
    }

    init {
        oldColor
        background = ColorDrawable(Color.TRANSPARENT)
        shadowView?.background = background
        this.post { dealParentClipChild() }

        AutoGetAttributeHelper.init<BottomTabLayoutAttr>(
            context,
            attrs,
            R.styleable.BottomTabLayout
        ).apply {
            this@BottomTabLayout.animViewId = animView ?: 0
        }
    }

    class BottomTabLayoutAttr {
        @IdRes
        val animView: Int? = null
    }
}
