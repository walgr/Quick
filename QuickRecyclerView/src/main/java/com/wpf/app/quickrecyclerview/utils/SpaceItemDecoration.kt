package com.wpf.app.quickrecyclerview.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SpaceItemDecoration(
    private val space: Int = 0,
    private val spaceType: Int = SpaceType.Center.type,
    private val includeFirst: Boolean = false,
    private val includeLast: Boolean = false,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter == null) return
        val pos = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager
        var startSpace = 0
        when (spaceType) {
            SpaceType.Center.type -> {
                startSpace = space / 2
            }

            SpaceType.Start.type -> {
                startSpace = space
            }

            SpaceType.End.type -> {
                startSpace = 0
            }
        }
        val allCount = parent.adapter!!.itemCount
        if (layoutManager is GridLayoutManager || layoutManager is StaggeredGridLayoutManager) {
            startSpace = space / 2          //保证同级view大小一致只能是Center
            val span = when (layoutManager) {
                is GridLayoutManager -> layoutManager.spanCount
                is StaggeredGridLayoutManager -> layoutManager.spanCount
                else -> 0
            }
            val orientation = when (layoutManager) {
                is GridLayoutManager -> layoutManager.orientation
                is StaggeredGridLayoutManager -> layoutManager.orientation
                else -> RecyclerView.VERTICAL
            }
            val allRows = (allCount + 1) / span
            val posMod = pos % span
            val posDiv = pos / span
            if (orientation == RecyclerView.VERTICAL) {
                if (posMod in 1 until span) {
                    outRect.left = startSpace
                }
                if (posMod in 0 until span - 1) {
                    outRect.right = space - startSpace
                }
                if (posDiv == 0) {
                    if (includeFirst) {
                        outRect.top = space
                    }
                } else if (posDiv in 1 until allRows) {
                    outRect.top = startSpace
                }
                if (posDiv in 0 until allRows - 1) {
                    outRect.bottom = space - startSpace
                } else if (posDiv == allRows - 1) {
                    if (includeLast) {
                        outRect.bottom = space
                    }
                }
            } else {
                if (posMod in 1 until span) {
                    outRect.top = startSpace
                }
                if (posMod in 0 until span - 1) {
                    outRect.bottom = space - startSpace
                }
                if (posDiv == 0) {
                    if (includeFirst) {
                        outRect.left = space
                    }
                } else if (posDiv in 1 until allRows) {
                    outRect.left = startSpace
                }
                if (posDiv in 0 until allRows - 1) {
                    outRect.right = space - startSpace
                } else if (posDiv == allRows - 1) {
                    if (includeLast) {
                        outRect.right = space
                    }
                }
            }
        } else if (layoutManager is LinearLayoutManager) {
            if (layoutManager.orientation == RecyclerView.VERTICAL) {
                if (pos == 0) {
                    if (includeFirst) {
                        outRect.top = space
                    }
                } else if (pos in 1 until allCount) {
                    outRect.top = startSpace
                }
                if (pos in 0 until allCount - 1) {
                    outRect.bottom = space - startSpace
                } else if (pos == allCount - 1) {
                    if (includeLast) {
                        outRect.bottom = space
                    }
                }
            } else {
                if (pos == 0) {
                    if (includeFirst) {
                        outRect.left = space
                    }
                } else if (pos in 1 until allCount) {
                    outRect.left = startSpace
                }
                if (pos in 0 until allCount - 1) {
                    outRect.right = space - startSpace
                } else if (pos == allCount - 1) {
                    if (includeLast) {
                        outRect.right = space
                    }
                }
            }
        }
    }
}