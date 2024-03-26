package com.wpf.app.quick.demo.wanandroid

import android.widget.LinearLayout
import android.widget.TextView
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.fragment
import com.wpf.app.quick.ability.ex.myLayout
import com.wpf.app.quick.ability.ex.viewPager
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quick.demo.model.ListRequest
import com.wpf.app.quick.demo.wanandroid.model.Article
import com.wpf.app.quick.demo.widgets.emptyview.TestEmptyView
import com.wpf.app.quickrecyclerview.listeners.requestData2List
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.log.LogUtil
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwidget.emptyview.EmptyHelper
import com.wpf.app.quickwork.ability.smartRefreshLayout
import com.wpf.app.quickwork.ability.tabLayout
import com.wpf.app.quickwork.ability.title

@GetClass
class WanAndroidHomeActivity : QuickActivity(
    contentView<LinearLayout> { quickView ->
        title("WanAndroid")
        tabLayout(matchWrapLayoutParams.reset(height = 44.dp()))
        viewPager(quickView) {
            fragment {
                smartRefreshLayout(upperLayerLayoutView = TestEmptyView(context)) { list, upperLayout ->
                    EmptyHelper.bind(list, emptyView = upperLayout?.forceTo())
                    requestData2List<ListRequest, Article> { requestData, callback ->
                        request(quickView.forceTo()) {
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
            }
            fragment {
                myLayout(layoutViewInContext = runOnContext {
                    TextView(it).apply {
                        text = "测试"
                    }
                })
            }
        }
    })