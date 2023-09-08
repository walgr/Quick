package com.wpf.app.quickrecyclerview

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickutil.CallbackList

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRefreshRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : QuickRecyclerView(mContext, attrs, defStyleAttr), RefreshView {
    override var refreshView: RefreshView? = null

    override fun getAdapter(): Adapter<*> {
        return getQuickAdapter()
    }

    @JvmField
    var requestData = RequestData(0)

    @JvmField
    val refreshCallback = object : CallbackList<QuickItemData> {
        override fun backData(data: List<QuickItemData>?) {
            refreshView?.onRefreshEnd(data)
            getQuickAdapter().mDataList?.clear()
            getQuickAdapter().appendList(data)
            requestData.loadDataSize(data?.size ?: 0)
            if (mDataChangeListener?.refreshFinish() != true) {
                adapter.notifyDataSetChanged()
            }
        }
    }

    @JvmField
    val loadMoreCallback = object : CallbackList<QuickItemData> {
        override fun backData(data: List<QuickItemData>?) {
            refreshView?.onLoadMoreEnd(data)
            appendList(data)
            requestData.loadDataSize(data?.size ?: 0)
            if (mDataChangeListener?.loadMoreFinish() != true) {
                adapter.notifyItemRangeInserted(
                    size() - (data?.size ?: 0), (data?.size ?: 0)
                )
            }
        }
    }

    private var mDataChangeListener: Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>? =
        null

    fun <Request : RequestData, Data : QuickItemData> setDataChangeListener(dataChangeListener: Request2ListWithView<Request, Data, QuickRefreshRecyclerView>) {
        mDataChangeListener = dataChangeListener
        (mDataChangeListener as? Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>)?.let {
            it.requestData = requestData
            it.refreshCallback = refreshCallback
            it.loadMoreCallback = loadMoreCallback
        }
    }

    override fun onRefresh() {
        requestData.refresh()
        (mDataChangeListener as? Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>)
            ?.requestAndCallback(this, requestData, refreshCallback)
    }

    override fun onLoadMore() {
        requestData.loadMore()
        (mDataChangeListener as? Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>)
            ?.requestAndCallback(this, requestData, loadMoreCallback)
    }
}