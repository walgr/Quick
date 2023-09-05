package com.wpf.app.quickrecyclerview.utils

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

abstract class ClickItemDecoration : RecyclerView.ItemDecoration() {

    private var gestureDetector: GestureDetector? = null
    private val gestureListener: GestureDetector.OnGestureListener = object : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent) {}
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return onTouchEvent(e)
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            return false
        }
//
//        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
//            return false
//        }

        override fun onLongPress(e: MotionEvent) {}

        override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            return false
        }

//        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//            return false
//        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (gestureDetector == null) {
            gestureDetector = GestureDetector(parent.context, gestureListener)
            parent.addOnItemTouchListener(object : OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//                    if (e.action != MotionEvent.ACTION_UP) {
//                        onTouchEvent(e)
//                    }
                    return gestureDetector?.onTouchEvent(e) ?: false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
//                    getStickyItem()?.onTouchEvent(e)
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

                }

            })
        }
    }

    private fun onTouchEvent(e: MotionEvent): Boolean {
        val stickyView = getStickyItem()
        stickyView?.let {
            if (e.y > (it.top - getStickyItemMarginTop()) && e.y < (it.bottom - getStickyItemMarginTop()) && e.x > it.left && e.x < it.right) {
                return if (onTouchEvent(it, e))
                    true
                else {
                    it.performClick()
                    true
                }
            }
        }
        return false
    }

    private fun onTouchEvent(view: View, e: MotionEvent): Boolean {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                return onTouchEvent(view.getChildAt(i), e)
            }
        } else {
            if (view.isClickable && e.x > view.left && e.x < view.right && e.y > view.top && e.y < view.bottom) {
                view.performClick()
                return true
            }
        }
        return false
    }

    abstract fun getStickyItem(): View?

    abstract fun getStickyItemMarginTop(): Float
}