package com.wpf.app.quickutil.recyclerview

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
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

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent) {}
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (gestureDetector == null) {
            gestureDetector = GestureDetector(parent.context, gestureListener)
            parent.addOnItemTouchListener(object : OnItemTouchListener {
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
        val stickyView = getStickyItemList()
        stickyView.forEach {
            if (it != null) {
                if (e.y > it.top && e.y < it.bottom && e.x > it.left && e.x < it.right) {
                    it.performClick()
                    return true
                }
            }
        }
        return false
    }

    abstract fun getStickyItemList(): List<View?>
}