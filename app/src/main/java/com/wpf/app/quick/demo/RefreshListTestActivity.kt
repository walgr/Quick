package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.view.View
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quick.demo.model.ListRequest
import com.wpf.app.quick.demo.wanandroid.model.Article
import com.wpf.app.quick.demo.widgets.emptyview.TestEmptyView
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickwidget.emptyview.EmptyHelper
import com.wpf.app.quickrecyclerview.helper.Request2RefreshView
import com.wpf.app.quickrecyclerview.listeners.requestData2List
import com.wpf.app.quickutil.log.LogUtil
import com.wpf.app.quickwork.activity.QuickTitleActivity

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class RefreshListTestActivity : QuickTitleActivity(R.layout.activity_refresh_list, titleName = "列表刷新页") {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.refreshLayout)
    var mSmartRefreshLayout: SmartRefreshLayout? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list)
    var mRecyclerView: QuickRefreshRecyclerView? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.emptyLayout)
    var emptyLayout: TestEmptyView? = null

    @SuppressLint("NonConstantResourceId")
    @BindData2View(R.id.list, helper = Request2RefreshView::class)
    val request2List = requestData2List<ListRequest, Article> { requestData, callback ->
        request(this) {
            首页文章列表(requestData.page, requestData.pageSize)
        }.success {
            callback.backData(it?.data?.datas, !it?.data?.datas.isNullOrEmpty())
        }.fail {
            callback.backData(null, false)
        }
    }.initRequestData {
        page = 0
    }.refreshFinish { hasMore ->
        LogUtil.e("下拉刷新请求结束")
        mSmartRefreshLayout?.finishRefresh()
        mSmartRefreshLayout?.setEnableLoadMore(hasMore)
        false
    }.loadMoreFinish { hasMore ->
        LogUtil.e("上拉加载请求结束")
        mSmartRefreshLayout?.finishLoadMore()
        mSmartRefreshLayout?.setEnableLoadMore(hasMore)
        false
    }

    override fun initView(view: View) {
        EmptyHelper.bind(mRecyclerView, emptyView = emptyLayout)
        mSmartRefreshLayout?.setOnRefreshListener { mRecyclerView?.onRefresh() }
        mSmartRefreshLayout?.setOnLoadMoreListener { mRecyclerView?.onLoadMore() }
        mSmartRefreshLayout?.autoRefresh()
    }
}