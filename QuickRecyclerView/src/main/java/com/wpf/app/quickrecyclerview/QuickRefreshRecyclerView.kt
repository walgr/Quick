package com.wpf.app.quickrecyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quicknetwork.utils.RequestCallback
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.nullDefault

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRefreshRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickRecyclerView(mContext, attrs, defStyleAttr), RefreshView {
    override var refreshView: RefreshView? = null

    override fun getAdapter(): Adapter<*> {
        return getQuickAdapter()
    }

    @JvmField
    var requestData = RequestData(0)

    @JvmField
    val refreshCallback = object : RequestCallback<QuickItemData> {
        @SuppressLint("NotifyDataSetChanged")
        override fun backData(data: List<QuickItemData>?, hasMore: Boolean) {
            refreshView?.onRefreshEnd(data)
            getQuickAdapter().mDataList?.clear()
            getQuickAdapter().appendList(data)
            requestData.loadDataSize(data?.size.nullDefault(0))
            if (mDataChangeListener?.refreshFinish(hasMore) != true) {
                adapter.notifyDataSetChanged()
            }
        }
    }

    @JvmField
    val loadMoreCallback = object : RequestCallback<QuickItemData> {
        override fun backData(data: List<QuickItemData>?, hasMore: Boolean) {
            refreshView?.onLoadMoreEnd(data)
            appendList(data)
            requestData.loadDataSize(data?.size.nullDefault(0))
            if (mDataChangeListener?.loadMoreFinish(hasMore) != true) {
                adapter.notifyItemRangeInserted(
                    size() - data?.size.nullDefault(0), data?.size.nullDefault(0)
                )
            }
        }
    }

    private var mDataChangeListener: Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>? =
        null

    fun setDataChangeListener(dataChangeListener: Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>) {
        mDataChangeListener = dataChangeListener
        mDataChangeListener.asTo<Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>>()?.let {
            it.requestData = requestData
            it.refreshCallback = refreshCallback
            it.loadMoreCallback = loadMoreCallback
        }
    }

    override fun onRefresh() {
        refreshView?.onRefresh()
        requestData.refresh()
        mDataChangeListener.asTo<Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>>()
            ?.requestAndCallback(this, requestData, refreshCallback)
    }

    override fun onLoadMore() {
        refreshView?.onLoadMore()
        requestData.loadMore()
        mDataChangeListener.asTo<Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>>()
            ?.requestAndCallback(this, requestData, loadMoreCallback)
    }
}