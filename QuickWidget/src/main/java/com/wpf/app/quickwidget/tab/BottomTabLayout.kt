package com.wpf.app.quickwidget.tab

import android.animation.Animator
import android.app.Activity
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
import androidx.core.view.children
import androidx.core.view.drawToBitmap
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickutil.activity.contentView
import com.wpf.app.quickutil.helper.anim
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.helper.getLocationInWindow
import com.wpf.app.quickutil.widget.onPageScrollStateChanged
import com.wpf.app.quickutil.widget.onPageScrolled
import com.wpf.app.quickwidget.quickview.QuickViewGroup
import com.wpf.app.quickwidget.R
import kotlin.math.max

class BottomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickViewGroup<LinearLayout>(context, attrs, defStyleAttr, addToParent = false, forceGenerics = true),
    GroupManager.OnGroupChangeListener {

    private val viewList: MutableList<View> = mutableListOf()
    private val viewDeque: ArrayDeque<View> = ArrayDeque()
    private var viewLocation: MutableMap<View, FloatArray> = mutableMapOf()
    private var viewBitmap: MutableMap<View, Bitmap> = mutableMapOf()
    private fun initChildView() {
        post {
            viewDeque.clear()
            shadowView?.children?.forEach { child ->
                child.findViewById<View>(animViewId)?.let {
                    viewLocation[it] = it.getLocationInWindow().let {
                        FloatArray(2).apply {
                            this[0] = it[0].toFloat()
                            this[1] = it[1].toFloat()
                        }
                    }
                    viewList.add(it)
                    if (it.measuredWidth != 0) {
                        viewBitmap.getOrPut(it) {
                            it.drawToBitmap()
                        }
                    }
                }
            }
            viewList.getOrNull(0)?.let {
                viewDeque.add(viewList[0])
            }
        }
    }

    private var viewCenterX: Float = 0f
        get() = if (isInEditMode) 40f else field
    private var viewHeight = 0
    private val oldCurView: View?
        get() = if (viewDeque.size <= 1) null else viewDeque.firstOrNull()
    private val curView: View?
        get() = viewDeque.lastOrNull()
    private var animViewId: Int = 0
    private var anim: Animator? = null
    override fun onChange(view: View) {
        if (animViewId == 0) return
        val animView = view.findViewById<View>(animViewId)
        animView.post {
            if (!isBindViewPager || scrollCurPosition == -1) {
                if (viewDeque.size == 2) {
                    viewDeque.removeFirst()
                }
                if (!viewDeque.contains(animView)) {
                    viewDeque.add(animView)
                }
            }
            circleR = max(animView.width, animView.height).toFloat()
            viewCenterX = (viewLocation[animView]?.first() ?: 0f) + animView.width / 2f
            viewHeight = animView.height
            contentAppend = 10f
            paddingTopH = 2 * contentR - circleR

            endX = viewCenterX - allPathWidth / 2f
            if (lastCurX == -1f) {
                onAnimProgress(if (isBindViewPager && scrollCurPosition != -1) 0f else 1f)
                //初始时不做动画
                curX = endX
            } else {
                startX = lastCurX
                if (scrollCurPosition != -1) {
                    return@post
                }
                anim?.cancel()
                anim = (startX..endX).anim(
                    200, interpolator = AccelerateInterpolator()
                ) {
                    onAnimProgress((it - startX) / (endX - startX))
                    curX = it
                }
            }
        }
    }

    private var isBindViewPager = false
    private var lastPositionOffsetPixels = -1
    private var scrollCurPosition = -1
    fun bindViewPager(viewPager: ViewPager?) {
        if (viewPager == null) return
        isBindViewPager = true
        post {
            viewPager.onPageScrollStateChanged {
                if (it == ViewPager.SCROLL_STATE_DRAGGING) {
                    scrollCurPosition = viewPager.currentItem
                } else if (it == ViewPager.SCROLL_STATE_IDLE) {
                    lastPositionOffsetPixels = -1
                }
            }
            viewPager.onPageScrolled { _, positionOffset, positionOffsetPixels ->
                if (positionOffsetPixels == 0 || scrollCurPosition == -1) return@onPageScrolled
                val isScrollRight = positionOffsetPixels >= lastPositionOffsetPixels
                if (lastPositionOffsetPixels == -1) {
                    lastPositionOffsetPixels = if (isScrollRight) 0 else positionOffsetPixels
                }

                if (isScrollRight) {
                    var rightPos = scrollCurPosition + 1
                    if (rightPos >= viewList.size) {
                        rightPos = viewList.size - 1
                    }
                    viewList.getOrNull(rightPos)?.let {
                        if (curView != it) {
                            if (viewDeque.size == 2) {
                                viewDeque.removeFirst()
                            }
                            viewDeque.add(it)
                        }
                    }
                } else {
                    var leftPos = scrollCurPosition - 1
                    if (leftPos < 0) {
                        leftPos = 0
                    }
                    viewList.getOrNull(leftPos)?.let {
                        if (oldCurView != it) {
                            if (viewDeque.size == 2) {
                                viewDeque.removeFirst()
                            }
                            viewDeque.add(it)
                        }
                    }
                }
                startX = viewLocation[oldCurView]!!.first()
                endX = viewLocation[curView]!!.first()
                val viewInterval = endX - startX
                onAnimProgress(if (isScrollRight) positionOffset else (1 - positionOffset))
                curX =
                    curAnimProcess * viewInterval + startX + oldCurView!!.width / 2 - allPathWidth / 2
            }
        }
    }

    private var curAnimProcess: Float = 0f
    private fun onAnimProgress(progress: Float) {
        if (progress < 0 || progress > 1) return
        curAnimProcess = progress
        if (curAnimProcess == -0.0f) {
            curAnimProcess = 0f
        }
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


    private var paddingTopH = 0f
        get() = if (isInEditMode) 2 * contentR - circleR else field

    private val paint by lazy {
        Paint().apply {
            color = oldColor
            strokeWidth = 10f
        }
    }
    private val bitmapPaint by lazy {
        Paint()
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

    private var startX = 0f
    private var endX = 0f
    private var curX = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var lastCurX: Float = -1f
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
        lastCurX = curX
        canvas.restoreToCount(sc)
    }

    private fun drawBgCircle(canvas: Canvas) {
        path.rewind()
        path.addRect(0f, 0f, width, height, Path.Direction.CW)
        circleTopPath.offset(curX - lastCurX, 0f)
        leftCirclePath.offset(curX - lastCurX, 0f)
        rightCirclePath.offset(curX - lastCurX, 0f)
        circlePath.offset(curX - lastCurX, 0f)
        path.op(circleTopPath, Path.Op.DIFFERENCE)
        path.op(leftCirclePath, Path.Op.DIFFERENCE)
        path.op(rightCirclePath, Path.Op.DIFFERENCE)
        path.op(circlePath, Path.Op.DIFFERENCE)
        contentCirclePath.offset(curX - lastCurX, 0f)
        path.addPath(contentCirclePath)
        canvas.drawPath(path, paint)
    }

    private fun drawAnimView(canvas: Canvas) {
        bitmapPaint.alpha =
            (255 * if (curAnimProcess > 0.5f) curAnimProcess else (1 - curAnimProcess)).toInt()
        viewBitmap[if (curAnimProcess > 0.5f) curView else oldCurView]?.let {
            canvas.drawBitmap(
                it,
                curX + it.width / 2 + smallCircleR,
                smallCircleR - it.width / 2,
                bitmapPaint
            )
        }
    }

    private fun dealParentClipChild() {
        if (context is Activity) {
            (context as Activity).contentView()?.asTo<ViewGroup>()?.get(0)?.let {
                it.asTo<ViewGroup>()?.clipChildren = false
            }
        } else {
            parent?.asTo<ViewGroup>()?.clipChildren = false
        }
    }

    init {
        oldColor
        background = ColorDrawable(Color.TRANSPARENT)
        shadowView?.background = background
        this.post { dealParentClipChild() }
        initChildView()
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
