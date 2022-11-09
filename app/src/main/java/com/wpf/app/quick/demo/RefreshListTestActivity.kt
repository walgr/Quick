package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.helper.Request2RefreshView
import com.wpf.app.quickrecyclerview.listeners.request2List

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class RefreshListTestActivity : QuickActivity(R.layout.activity_refresh_list, titleName = "列表刷新页") {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.refreshLayout)
    var mSmartRefreshLayout: SmartRefreshLayout? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list)
    var mRecyclerView: QuickRefreshRecyclerView? = null

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.list, helper = Request2RefreshView::class)
    val request2List = request2List { requestData, callback ->
        request {
            首页文章列表(requestData.page)
        }.success {
            callback.callback(it?.data?.datas)
        }.after {
            if (requestData.isRefresh) {
                mSmartRefreshLayout?.finishRefresh()
            } else {
                mSmartRefreshLayout?.finishLoadMore()
            }
        }
    }

    override fun initView() {
        mSmartRefreshLayout?.setOnRefreshListener { mRecyclerView?.onRefresh() }
        mSmartRefreshLayout?.setOnLoadMoreListener { mRecyclerView?.onLoadMore() }
        mSmartRefreshLayout?.autoRefresh()
    }
}