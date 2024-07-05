package com.wpf.app.quickrecyclerview.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickutil.utils.LogUtil
import com.wpf.app.quickutil.helper.generic.nullDefault

class SpaceItemDecoration(
    var space: Int = 0,
    var spaceType: Int = SpaceType.Center.type,
    var includeFirst: Boolean = false,
    var includeLast: Boolean = false,
) : RecyclerView.ItemDecoration() {
    private val TAG = "SpaceItemDecoration"
    private val DEBUG = false
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter == null) return
        val oldPos = parent.getChildAdapterPosition(view)
        var pos = oldPos
        if (DEBUG) {
            LogUtil.e(TAG, "当前原始pos:${pos}")
        }
        var headerSize = 0
        var itemData: QuickItemData? = null
        if (parent.adapter is QuickAdapter) {
            val quickAdapter = parent.adapter as QuickAdapter
            itemData = quickAdapter.getData(pos)
            if (itemData?.dealSpaceItemDecoration(pos) == false) return
            headerSize = (parent.adapter as QuickAdapter).headerViews.size
            if (pos < headerSize) {
                return
            }
            pos -= headerSize
        }
        if (DEBUG) {
            LogUtil.e(TAG, "当前新pos:${pos}")
        }
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
        val allCount = parent.adapter!!.itemCount - headerSize
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
            val allRows = itemData?.customAllRows(allCount, span)
                .nullDefault((allCount + 1) / span)
            val posMod = itemData?.customColumn(oldPos, span)
                .nullDefault(pos % span)     //列数
            val posDiv = itemData?.customRow(oldPos, span)
                .nullDefault(pos / span)     //行数
            if (DEBUG) {
                LogUtil.e(TAG, "当前新pos总行数:${allRows}-列数:${posMod}-行数:${posDiv}")
            }
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