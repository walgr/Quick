package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quick.demo.model.ListRequest
import com.wpf.app.quick.demo.wanandroid.model.Article
import com.wpf.app.quick.demo.widgets.emptyview.TestEmptyView
import com.wpf.app.quickrecyclerview.listeners.requestData2List
import com.wpf.app.quickutil.log.LogUtil
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwork.ability.smartRefreshLayout
import com.wpf.app.quickwork.ability.title

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class RefreshListTestActivity : QuickActivity(
    contentView<LinearLayout> { quickView ->
        title("列表刷新页")
        smartRefreshLayout {
            requestData2List<ListRequest, Article> { requestData, callback ->
                request(quickView.forceTo()) {
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
                finishRefresh()
                    .setEnableLoadMore(hasMore)
                false
            }.loadMoreFinish { hasMore ->
                LogUtil.e("上拉加载请求结束")
                finishLoadMore()
                setEnableLoadMore(hasMore)
                false
            }
        }
//        myLayout(R.layout.activity_refresh_list)
    }
) {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.emptyLayout)
    var emptyLayout: TestEmptyView? = null

    override fun initView(view: View) {
        super.initView(view)
//        EmptyHelper.bind(mRecyclerView, emptyView = emptyLayout)
    }
}