package com.wpf.app.base.ability.helper

import android.graphics.drawable.Drawable
import android.view.ViewGroup.LayoutParams
import androidx.annotation.ColorInt
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.createViewGroupScope
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.wrapMarginLayoutParams
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

fun ContextScope.circle(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    @ColorInt borderColor: Int? = null,
    borderWidth: Float = 0f,        //单位px
    builder: (ViewGroupScope<CircleView>.() -> Unit)? = null,
): CircleView {
    val view = CircleView(context)
    borderColor?.let {
        view.borderColor = it
        view.borderWidth = borderWidth
    }
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.roundRect(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    background: Drawable? = null,
    radius: Float? = null,
    topLeftRadius: Float = 0f,
    topRightRadius: Float = 0f,
    bottomRightRadius: Float = 0f,
    bottomLeftRadius: Float = 0f,
    @ColorInt borderColor: Int? = null,
    borderWidth: Float = 0f,        //单位px
    builder: (ViewGroupScope<RoundRectView>.() -> Unit)? = null,
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
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.clipCorner(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    cutSize: Float? = null,
    topLeftCutSize: Float = 0f,
    topRightCutSize: Float = 0f,
    bottomRightCutSize: Float = 0f,
    bottomLeftCutSize: Float = 0f,
    builder: (ViewGroupScope<CutCornerView>.() -> Unit)? = null,
): CutCornerView {
    val view = CutCornerView(context)
    view.topLeftCutSize = cutSize ?: topLeftCutSize
    view.topRightCutSize = cutSize ?: topRightCutSize
    view.bottomRightCutSize = cutSize ?: bottomRightCutSize
    view.bottomLeftCutSize = cutSize ?: bottomLeftCutSize
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.arc(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    height: Float = 0f,       //单位px 负反方向
    @ArcPosition position: Int = ArcView.POSITION_BOTTOM,
    builder: (ViewGroupScope<ArcView>.() -> Unit)? = null,
): ArcView {
    val view = ArcView(context)
    view.arcHeight = height
    view.arcPosition = position
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.diagonal(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    angle: Float = 0f,       //单位px 负反方向
    @DiagonalView.DiagonalPosition position: Int = DiagonalView.POSITION_BOTTOM,
    builder: (ViewGroupScope<DiagonalView>.() -> Unit)? = null,
): DiagonalView {
    val view = DiagonalView(context)
    view.diagonalAngle = angle
    view.diagonalPosition = position
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.triangle(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    percentLeft: Float = 0f,
    percentRight: Float = 0f,
    paddingBottom: Float = 0.5f,
    builder: (ViewGroupScope<TriangleView>.() -> Unit)? = null,
): TriangleView {
    val view = TriangleView(context)
    view.percentLeft = percentLeft
    view.percentRight = percentRight
    view.percentBottom = paddingBottom
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.bubble(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    arrowWidth: Float = 10f.dp,
    arrowHeight: Float = 10f.dp,
    borderRadius: Float = 10f.dp,
    positionPer: Float = 0.5f,
    position: Int = BubbleView.POSITION_BOTTOM,
    builder: (ViewGroupScope<BubbleView>.() -> Unit)? = null,
): BubbleView {
    val view = BubbleView(context)
    view.position = position
    view.arrowWidth = arrowWidth
    view.arrowHeight = arrowHeight
    view.borderRadius = borderRadius
    view.setPositionPer(positionPer)
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.star(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    points: Int = 5,
    builder: (ViewGroupScope<StarView>.() -> Unit)? = null,
): StarView {
    val view = StarView(context)
    view.noOfPoints = points
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.polygon(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    sides: Int = 4,
    builder: (ViewGroupScope<PolygonView>.() -> Unit)? = null,
): PolygonView {
    val view = PolygonView(context)
    view.noOfSides = sides
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

fun ContextScope.dottedEdgesCutCorner(
    layoutParams: LayoutParams = wrapMarginLayoutParams(),
    dotRadius: Float = 0f,
    dotSpace: Float = 0f,
    position: Int = DottedEdgesCutCornerView.POSITION_NONE,
    topLeftCutSize: Float = 0f,
    topRightCutSize: Float = 0f,
    bottomLeftCutSize: Float = 0f,
    bottomRightCutSize: Float = 0f,
    builder: (ViewGroupScope<DottedEdgesCutCornerView>.() -> Unit)? = null,
): DottedEdgesCutCornerView {
    val view = DottedEdgesCutCornerView(context)
    view.dotRadius = dotRadius
    view.dotSpacing = dotSpace
    view.addDotEdgePosition(position)
    view.topLeftCutSize = topLeftCutSize
    view.topRightCutSize = topRightCutSize
    view.bottomLeftCutSize = bottomLeftCutSize
    view.bottomRightCutSize = bottomRightCutSize
    builder?.invoke(view.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

