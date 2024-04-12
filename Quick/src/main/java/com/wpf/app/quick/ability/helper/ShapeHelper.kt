package com.wpf.app.quick.ability.helper

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.annotation.ColorInt
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.widget.smartLayoutParams
import io.github.florent37.shapeofview.shapes.ArcView
import io.github.florent37.shapeofview.shapes.ArcView.ArcPosition
import io.github.florent37.shapeofview.shapes.BubbleView
import io.github.florent37.shapeofview.shapes.CircleView
import io.github.florent37.shapeofview.shapes.CutCornerView
import io.github.florent37.shapeofview.shapes.DiagonalView
import io.github.florent37.shapeofview.shapes.DottedEdgesCutCornerView
import io.github.florent37.shapeofview.shapes.PolygonView
import io.github.florent37.shapeofview.shapes.RoundRectView
import io.github.florent37.shapeofview.shapes.StarView
import io.github.florent37.shapeofview.shapes.TriangleView

fun ViewGroup.circle(
    layoutParams: LayoutParams = smartLayoutParams(),
    @ColorInt borderColor: Int? = null,
    borderWidth: Float = 0f,        //单位px
    builder: (CircleView.() -> Unit)? = null,
): CircleView {
    val view = CircleView(context)
    borderColor?.let {
        view.borderColor = it
        view.borderWidth = borderWidth
    }
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.roundRect(
    layoutParams: LayoutParams = smartLayoutParams(),
    background: Drawable? = null,
    radius: Float? = null,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int? = null,
    borderWidth: Float = 0f,        //单位px
    builder: (RoundRectView.() -> Unit)? = null,
): RoundRectView {
    val view = RoundRectView(context)
    view.background = background
    borderColor?.let {
        view.setBorderColor(it)
        view.borderWidth = borderWidth
    }
    view.topLeftRadius = radius ?: topLeftRadius
    view.topRightRadius = radius ?: topRightRadius
    view.bottomRightRadius = radius ?: bottomRightRadius
    view.bottomLeftRadius = radius ?: bottomLeftRadius
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.clipCorner(
    layoutParams: LayoutParams = smartLayoutParams(),
    cutSize: Float? = null,
    topLeftCutSize: Float = 0f,
    topRightCutSize: Float = 0f,
    bottomRightCutSize: Float = 0f,
    bottomLeftCutSize: Float = 0f,
    builder: (CutCornerView.() -> Unit)? = null,
): CutCornerView {
    val view = CutCornerView(context)
    view.topLeftCutSize = cutSize ?: topLeftCutSize
    view.topRightCutSize = cutSize ?: topRightCutSize
    view.bottomRightCutSize = cutSize ?: bottomRightCutSize
    view.bottomLeftCutSize = cutSize ?: bottomLeftCutSize
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.arc(
    layoutParams: LayoutParams = smartLayoutParams(),
    height: Float = 0f,       //单位px 负反方向
    @ArcPosition position: Int = ArcView.POSITION_BOTTOM,
    builder: (ArcView.() -> Unit)? = null,
): ArcView {
    val view = ArcView(context)
    view.arcHeight = height
    view.arcPosition = position
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.diagonal(
    layoutParams: LayoutParams = smartLayoutParams(),
    angle: Float = 0f,       //单位px 负反方向
    @DiagonalView.DiagonalPosition position: Int = DiagonalView.POSITION_BOTTOM,
    builder: (DiagonalView.() -> Unit)? = null,
): DiagonalView {
    val view = DiagonalView(context)
    view.diagonalAngle = angle
    view.diagonalPosition = position
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.triangle(
    layoutParams: LayoutParams = smartLayoutParams(),
    percentLeft: Float = 0f,
    percentRight: Float = 0f,
    paddingBottom: Float = 0.5f,
    builder: (TriangleView.() -> Unit)? = null,
): TriangleView {
    val view = TriangleView(context)
    view.percentLeft = percentLeft
    view.percentRight = percentRight
    view.percentBottom = paddingBottom
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.bubble(
    layoutParams: LayoutParams = smartLayoutParams(),
    arrowWidth: Float = 10.dpF(),
    arrowHeight: Float = 10.dpF(),
    borderRadius: Float = 10.dpF(),
    positionPer: Float = 0.5f,
    position: Int = BubbleView.POSITION_BOTTOM,
    builder: (BubbleView.() -> Unit)? = null,
): BubbleView {
    val view = BubbleView(context)
    view.position = position
    view.arrowWidth = arrowWidth
    view.arrowHeight = arrowHeight
    view.borderRadius = borderRadius
    view.setPositionPer(positionPer)
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.star(
    layoutParams: LayoutParams = smartLayoutParams(),
    points: Int = 5,
    builder: (StarView.() -> Unit)? = null,
): StarView {
    val view = StarView(context)
    view.noOfPoints = points
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.polygon(
    layoutParams: LayoutParams = smartLayoutParams(),
    sides: Int = 4,
    builder: (PolygonView.() -> Unit)? = null,
): PolygonView {
    val view = PolygonView(context)
    view.noOfSides = sides
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

fun ViewGroup.dottedEdgesCutCorner(
    layoutParams: LayoutParams = smartLayoutParams(),
    dotRadius: Float = 0f,
    dotSpace: Float = 0f,
    position: Int = DottedEdgesCutCornerView.POSITION_NONE,
    topLeftCutSize: Float = 0f,
    topRightCutSize: Float = 0f,
    bottomLeftCutSize: Float = 0f,
    bottomRightCutSize: Float = 0f,
    builder: (DottedEdgesCutCornerView.() -> Unit)? = null,
): DottedEdgesCutCornerView {
    val view = DottedEdgesCutCornerView(context)
    view.dotRadius = dotRadius
    view.dotSpacing = dotSpace
    view.addDotEdgePosition(position)
    view.topLeftCutSize = topLeftCutSize
    view.topRightCutSize = topRightCutSize
    view.bottomLeftCutSize = bottomLeftCutSize
    view.bottomRightCutSize = bottomRightCutSize
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}

