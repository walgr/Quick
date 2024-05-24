package com.wpf.app.quickwidget.wheel

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.other.nullDefault
import com.wpf.app.quickutil.widget.scrollToPositionAndOffset
import com.wpf.app.quickwidget.R
import com.wpf.app.quickwidget.selectview.QuickSelectRecyclerView

class WheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private var maxShowSize: Int = 9,               //最多展示多少个 必须是奇数
    maskBackground: Drawable? = null
) : FrameLayout(
    context, attrs, defStyleAttr
) {

    private var list: QuickSelectRecyclerView? = null
    private var mask: View? = null

    private var oldFirstItemPos: Int = -1

    init {
        R.layout.wheel_layout.toView(context, this, true)
        list = findViewById(R.id.list)
        mask = findViewById(R.id.mask)
        maskBackground?.let {
            mask?.background = it
        }
        list?.setHasFixedSize(true)

        list?.layoutManager = WheelLayoutManager(context)
        LinearSnapHelper().attachToRecyclerView(list)
        list?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItemPos = recyclerView.layoutManager?.forceTo<LinearLayoutManager>()
                    ?.findFirstVisibleItemPosition().nullDefault(0)
                if (oldFirstItemPos != firstItemPos) {
                    oldFirstItemPos = firstItemPos
                    repeat(maxShowSize) {
                        list?.getQuickAdapter()?.getData(firstItemPos + it)
                            ?.forceTo<WheelItemData>()?.apply {
                            if (it == getOffsetSize()) {
                                showCenterItem()
                            } else {
                                showOtherItem()
                            }
                        }
                    }
                }
            }
        })
    }

    fun setData(data: List<WheelItemData>) {
        list?.setNewData(addTopBottomEmptyData(data))
        setCenterPos(0)
        list?.post {
            val itemHeight = list!!.getChildAt(getOffsetSize()).measuredHeight
            list?.updateLayoutParams<LayoutParams> {
                height = itemHeight * maxShowSize
            }
            mask?.updateLayoutParams<LayoutParams> {
                height = itemHeight
            }
        }
    }

    fun setCenterPos(pos: Int) {
        if (list == null) return
        list?.post {
            list?.scrollToPositionAndOffset(pos)
        }
    }

    fun setCenterPosById(id: String) {
        if (list == null) return
        val findItem = list?.getRealTypeData<WheelItemData>()?.find { it.id == id } ?: return
        setCenterPos(list!!.getDataPos(findItem) - getOffsetSize())
    }

    fun setCenterPosByName(name: String) {
        if (list == null) return
        val findItem = list?.getRealTypeData<WheelItemData>()?.find { it.name == name } ?: return
        setCenterPos(list!!.getDataPos(findItem) - getOffsetSize())
    }

    fun getSelectItem(): WheelItemData {
        if (list == null) {
            throw RuntimeException("list is null")
        }
        return list!!.getSelectAdapter().getData(
            list!!.layoutManager!!.forceTo<LinearLayoutManager>()
                .findFirstCompletelyVisibleItemPosition() + getOffsetSize()
        )!!.forceTo<WheelItemData>()
    }

    private fun addTopBottomEmptyData(data: List<WheelItemData>): MutableList<WheelItemData> {
        val newData = mutableListOf<WheelItemData>()
        val dataCls = data.first().javaClass
        repeat(getOffsetSize()) {
            newData.add(dataCls.newInstance())
        }
        newData.addAll(data)
        repeat(getOffsetSize()) {
            newData.add(dataCls.newInstance())
        }
        return newData
    }

    private fun getOffsetSize() = maxShowSize / 2
}