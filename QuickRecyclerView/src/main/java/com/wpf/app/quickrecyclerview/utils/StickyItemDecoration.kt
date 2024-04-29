package com.wpf.app.quickrecyclerview.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickutil.other.nullDefault


class StickyItemDecoration(
    private val mStickyHelper: StickyHelper,
) : ClickItemDecoration() {

    /**
     * 吸附的itemView
     */
    private var mStickyItemView: View? = null

    /**
     * 吸附itemView 距离顶部
     */
    private var mStickyItemViewMarginTop = 0F

    /**
     * layout manager
     */
    private var mLayoutManager: LinearLayoutManager? = null

    /**
     * paint
     */
    private var mPaint: Paint? = null

    init {
        initPaint()
    }

    /**
     * init paint
     */
    private fun initPaint() {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
    }

    private var firstLoad = false
    private var allStickyPosList = mutableListOf<Int>()
    private var stickyItemViewPos = -2
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.adapter!!.itemCount <= 0 || parent.layoutManager !is LinearLayoutManager) return
        if (mLayoutManager == null) {
            mLayoutManager = parent.layoutManager as? LinearLayoutManager
        }
        if (mLayoutManager == null || parent.adapter == null) return
        if (!firstLoad) {
            parent.adapter!!.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart, itemCount)
                    allStickyPosList = mStickyHelper.getAllStickyList(parent.adapter!!)
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    super.onItemRangeChanged(positionStart, itemCount, payload)
                    allStickyPosList = mStickyHelper.getAllStickyList(parent.adapter!!)
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    allStickyPosList = mStickyHelper.getAllStickyList(parent.adapter!!)
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                    allStickyPosList = mStickyHelper.getAllStickyList(parent.adapter!!)
                }

                override fun onChanged() {
                    super.onChanged()
                    allStickyPosList = mStickyHelper.getAllStickyList(parent.adapter!!)
                }
            })
            allStickyPosList = mStickyHelper.getAllStickyList(parent.adapter!!)
            firstLoad = true
        }
        val firstViewPos = mLayoutManager!!.findFirstVisibleItemPosition()
        var stickyItemViewPos = -1
        if (allStickyPosList.size == 1) {

            stickyItemViewPos = allStickyPosList[0]
        } else {
            allStickyPosList.forEachIndexed { index, i ->
                val nextData = allStickyPosList.getOrNull(index + 1).nullDefault(-1)
                if (firstViewPos in (i)..<nextData) {
                    stickyItemViewPos = i
                }
            }
        }
        if (stickyItemViewPos == -1) return
        if (this.stickyItemViewPos != stickyItemViewPos) {
            mStickyItemView = getItemViewByPosition(parent, stickyItemViewPos)
            this.stickyItemViewPos = stickyItemViewPos
        }
        mStickyItemViewMarginTop = 0F
        val nextStickyItemViewPos =
            allStickyPosList.getOrNull(allStickyPosList.indexOf(stickyItemViewPos) + 1)
                .nullDefault(-1)
        if (nextStickyItemViewPos != -1) {
            val nextStickyItemViewTop =
                mLayoutManager!!.findViewByPosition(nextStickyItemViewPos)?.top.nullDefault(-1)
            if (nextStickyItemViewTop <= mStickyItemView?.height.nullDefault(0)) {
                mStickyItemViewMarginTop =
                    mStickyItemView?.height.nullDefault(0) - nextStickyItemViewTop.toFloat()
            }
        }
        drawStickyItemView(c)
    }

    private fun getItemViewByPosition(recyclerView: RecyclerView, position: Int): View {
        recyclerView.adapter!!.apply {
            val viewHolder = onCreateViewHolder(recyclerView, this.getItemViewType(position))
            val itemView = viewHolder.itemView
            onBindViewHolder(viewHolder, position)
            measureLayoutStickyItemView(itemView, recyclerView.width)
            return itemView
        }
    }

    /**
     * 计算布局吸附的itemView
     * @param parentWidth
     */
    private fun measureLayoutStickyItemView(stickyView: View, parentWidth: Int) {
        if (!stickyView.isLayoutRequested) return
        val widthSpec: Int = View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY)
        val heightSpec: Int
        val layoutParams: ViewGroup.LayoutParams? = stickyView.layoutParams
        heightSpec = if (layoutParams != null && layoutParams.height > 0) {
            View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        stickyView.measure(widthSpec, heightSpec)
        stickyView.layout(
            0,
            0,
            stickyView.measuredWidth,
            stickyView.measuredHeight
        )
    }

    /**
     * 绘制吸附的itemView
     * @param canvas
     */
    private fun drawStickyItemView(canvas: Canvas) {
        if (mStickyItemView == null) return
        val saveCount: Int = canvas.save()
        canvas.translate(0f, -mStickyItemViewMarginTop)
        mStickyItemView?.draw(canvas)
        canvas.restoreToCount(saveCount)
    }

    override fun getStickyItem(): View? {
        return mStickyItemView
    }

    override fun getStickyItemMarginTop(): Float {
        return mStickyItemViewMarginTop
    }
}