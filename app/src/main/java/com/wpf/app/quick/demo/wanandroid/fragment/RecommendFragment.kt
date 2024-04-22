package com.wpf.app.quick.demo.wanandroid.fragment

import android.widget.FrameLayout
import com.wpf.app.quick.ability.QuickFragment
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quick.demo.model.ListRequest
import com.wpf.app.quick.demo.wanandroid.model.Article
import com.wpf.app.quick.demo.widgets.emptyview.TestEmptyView
import com.wpf.app.quickrecyclerview.listeners.requestData2List
import com.wpf.app.quickutil.log.LogUtil
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwidget.emptyview.EmptyHelper
import com.wpf.app.quickwork.ability.helper.smartRefreshList

class RecommendFragment: QuickFragment(
    contentView<FrameLayout> { 
        smartRefreshList(upperLayerLayoutView = TestEmptyView(context), autoRefresh = true) { list, upperLayout ->
            EmptyHelper.bind(list, emptyView = upperLayout?.forceTo())
            requestData2List<ListRequest, Article> { requestData, callback ->
                request(self.forceTo()) {
                    homePageList(requestData.page, requestData.pageSize)
                }.success {
                    callback.backData(it?.data?.datas, !it?.data?.datas.isNullOrEmpty())
                }.fail {
                    callback.backData(null, false)
                }
            }.initRequestData {
                page = 0
            }.refreshFinish { hasMore ->
                LogUtil.e("下拉刷新请求结束")
                finishRefresh().setEnableLoadMore(hasMore)
                false
            }.loadMoreFinish { hasMore ->
                LogUtil.e("上拉加载请求结束")
                finishLoadMore().setEnableLoadMore(hasMore)
                false
            }
        }
    })