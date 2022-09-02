package com.wpf.app.quick.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quick.utils.Callback
import com.wpf.app.quick.widgets.recyclerview.data.QuickItemData
import com.wpf.app.quick.widgets.recyclerview.data.RequestData
import com.wpf.app.quick.widgets.recyclerview.listeners.DataChangeOnListener

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRefreshRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : QuickRecyclerView(mContext, attrs, defStyleAttr) {

    @JvmField
    var mRequestData = RequestData(0)

    private var mDataChangeListener: DataChangeOnListener<out RequestData, out QuickItemData>? = null

    fun <Request : RequestData, Data : QuickItemData> setDataChangeListener(dataChangeListener: DataChangeOnListener<Request, Data>) {
        mDataChangeListener = dataChangeListener
    }

    fun onRefresh() {
        mRequestData.refresh()
        (mDataChangeListener as? DataChangeOnListener<RequestData, QuickItemData>)?.onRefresh(
            mRequestData, object : Callback<QuickItemData> {
                override fun callback(data: List<QuickItemData>?) {
                    setNewData(data)
                    adapter.notifyDataSetChanged()
                }

            })
        mDataChangeListener?.refreshFinish()

    }

    fun onLoadMore() {
        mRequestData.loadMore()
        (mDataChangeListener as? DataChangeOnListener<RequestData, QuickItemData>)?.onLoadMore(
            mRequestData,
            object : Callback<QuickItemData> {
                override fun callback(data: List<QuickItemData>?) {
                    appendList(data)
                    adapter.notifyItemRangeInserted(
                        size() - (data?.size ?: 0), (data?.size ?: 0)
                    )
                }
            })
        mDataChangeListener?.loadMoreFinish()
    }
}