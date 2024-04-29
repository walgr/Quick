package com.wpf.app.quickrecyclerview.utils

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.widget.QuickHeaderShadow

abstract class ClickItemDecoration : RecyclerView.ItemDecoration() {

    private var gestureDetector: GestureDetector? = null
    private val gestureListener: GestureDetector.OnGestureListener =
        object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                return false
            }

            override fun onShowPress(e: MotionEvent) {}
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return onTouchEvent(e)
            }

            //        override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
//            return false
//        }
//
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float,
            ): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent) {}

//        override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
//            return false
//        }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float,
            ): Boolean {
                return false
            }
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (gestureDetector == null) {
            gestureDetector = GestureDetector(parent.context, gestureListener)
            parent.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return gestureDetector?.onTouchEvent(e) ?: false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

                }

            })
        }
    }

    private fun onTouchEvent(e: MotionEvent): Boolean {
        getStickyItem()?.let {
            var top = it.top
            var bottom = it.bottom
            if (it is QuickHeaderShadow) {
                top = 0
                bottom = it.height
            }
            if (it.isClickable && e.y > (top - getStickyItemMarginTop()) && e.y < (bottom - getStickyItemMarginTop()) && e.x > it.left && e.x < it.right) {
                it.performClick()
                return true
            }
        }
        return false
    }

    private fun onTouchEvent(view: View, e: MotionEvent): Boolean {
        if (e.x > view.left && e.x < view.right && e.y > view.top && e.y < view.bottom) {
            view.performClick()
            return true
        }
        return false
    }

    abstract fun getStickyItem(): View?

    abstract fun getStickyItemMarginTop(): Float
}