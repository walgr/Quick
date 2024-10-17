package com.wpf.app.quickwork.request2list

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quicknetwork.utils.RequestCallback
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.listeners.RefreshResult
import com.wpf.app.quickutil.helper.generic.asTo
import com.wpf.app.quickutil.helper.generic.nullDefault

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRefreshRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickRecyclerView(mContext, attrs, defStyleAttr), RefreshResult {

    override val onRefresh: MutableList<(curData: List<*>?) -> Unit> = mutableListOf()
    override val onLoadMore: MutableList<(curData: List<*>?) -> Unit> = mutableListOf()
    override val onRefreshEnd: MutableList<(data: List<*>?) -> Unit> = mutableListOf()
    override val onLoadMoreEnd: MutableList<(data: List<*>?) -> Unit> = mutableListOf()
    override var onRefreshError: MutableList<(e: Throwable) -> Unit> = mutableListOf()
    override var onLoadMoreError: MutableList<(e: Throwable) -> Unit> = mutableListOf()

    private var requestManager: Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>? =
        null

    fun setRequestManager(requestManager: Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>) {
        this.requestManager = requestManager
        this.requestManager.asTo<Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>>()?.let {
            it.requestData = it.requestData ?: RequestData(0)
            it.refreshCallback = it.refreshCallback ?: object : RequestCallback<QuickItemData> {
                @SuppressLint("NotifyDataSetChanged")
                override fun backData(data: List<QuickItemData>?, hasMore: Boolean) {
                    getQuickAdapter().mDataList?.clear()
                    getQuickAdapter().appendList(data)
                    requestManager.requestData?.loadDataSize(data?.size.nullDefault(0))
                    onRefreshEnd(data)
                    if (!requestManager.refreshFinish(hasMore)) {
                        getQuickAdapter().notifyDataSetChanged()
                    }
                }
            }
            it.loadMoreCallback = it.loadMoreCallback ?: object : RequestCallback<QuickItemData> {
                override fun backData(data: List<QuickItemData>?, hasMore: Boolean) {
                    appendList(data)
                    requestManager.requestData?.loadDataSize(data?.size.nullDefault(0))
                    onLoadMoreEnd(data)
                    if (!requestManager.loadMoreFinish(hasMore)) {
                        getQuickAdapter().notifyItemRangeInserted(
                            size() - data?.size.nullDefault(0), data?.size.nullDefault(0)
                        )
                    }
                }
            }
        }
    }
    override fun onRefresh() {
        onRefresh(getQuickAdapter().mDataList)
        requestManager?.requestData?.refresh()
        requestManager?.manualRequest()
        requestManager?.baseRequest?.error {
            onRefreshError(it)
        }
    }

    override fun onLoadMore() {
        onLoadMore(getQuickAdapter().mDataList)
        requestManager?.requestData?.loadMore()
        requestManager?.manualRequest()
        requestManager?.baseRequest?.error {
            onLoadMoreError(it)
        }
    }
}