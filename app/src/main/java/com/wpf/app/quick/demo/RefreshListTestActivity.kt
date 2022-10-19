package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.model.ListRequest
import com.wpf.app.quick.demo.model.RefreshItem
import com.wpf.app.quickutil.Callback

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class RefreshListTestActivity : QuickActivity(R.layout.activity_refresh_list, titleName = "列表刷新页") {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.refreshLayout)
    var mSmartRefreshLayout: SmartRefreshLayout? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list)
    var mRecyclerView: com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView? = null
    override fun initView() {
        mRecyclerView?.mRequestData = ListRequest(0)
        mRecyclerView?.setDataChangeListener(object :
            com.wpf.app.quickrecyclerview.listeners.DataChangeOnListener<ListRequest, RefreshItem> {
            override fun onLoadMore(requestData: ListRequest, callback: Callback<RefreshItem>) {
                if (requestData.page > 2) {
                    mSmartRefreshLayout?.setEnableLoadMore(false)
                }
                Thread {
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    mSmartRefreshLayout?.post {
                        mSmartRefreshLayout?.finishLoadMore()
                        callback.callback(arrayListOf(RefreshItem(), RefreshItem(), RefreshItem(),RefreshItem(), RefreshItem(), RefreshItem(),RefreshItem(), RefreshItem(), RefreshItem()))
                    }
                }.start()
            }

            override fun onRefresh(requestData: ListRequest, callback: Callback<RefreshItem>) {
                Thread {
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    mSmartRefreshLayout?.post {
                        mSmartRefreshLayout?.finishRefresh()
                        callback.callback(arrayListOf(RefreshItem(), RefreshItem(),RefreshItem(), RefreshItem(), RefreshItem(),RefreshItem(), RefreshItem(), RefreshItem(),RefreshItem(), RefreshItem(), RefreshItem(), RefreshItem(), RefreshItem(), RefreshItem(),RefreshItem(), RefreshItem(), RefreshItem(),RefreshItem(), RefreshItem(), RefreshItem()))
                    }
                }.start()
            }

        })
        mSmartRefreshLayout?.setOnRefreshListener { mRecyclerView?.onRefresh() }
        mSmartRefreshLayout?.setOnLoadMoreListener { mRecyclerView?.onLoadMore() }
        mRecyclerView?.onRefresh()
    }
}