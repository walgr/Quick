package com.wpf.app.quick.demo.wanandroid

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.fragment
import com.wpf.app.quick.ability.ex.viewFragment
import com.wpf.app.quick.ability.ex.myLayout
import com.wpf.app.quick.ability.ex.viewPager
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.annotations.tab.TabInit
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.wanandroid.fragment.RecommendFragment
import com.wpf.app.quick.helper.getActivity
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickwidget.tab.TabManagerProvider
import com.wpf.app.quickwork.ability.tabLayout
import com.wpf.app.quickwork.ability.textButton
import com.wpf.app.quickwork.ability.title
import com.wpf.app.quickwork.widget.QuickTitleView

@GetClass
class WanAndroidHomeActivity : QuickActivity(contentView<LinearLayout> { quickView ->
    title("WanAndroid") {
        textButton("登录", {

        })
        setCommonClickListener(object : QuickTitleView.CommonClickListener {
            override fun onBackClick(view: View) {
                super.onBackClick(view)
                quickView.getActivity().setResult(RESULT_OK)
            }
        })
    }
    val tabNames = arrayOf("推荐", "专题")
    val tabLayout = tabLayout(layoutParams = matchWrapLayoutParams.reset(height = 44.dp()))
    val viewPager = viewPager(quickView) {
        fragment(RecommendFragment())
        viewFragment {
            myLayout(layoutViewInContext = runOnContext {
                TextView(it).apply {
                    text = "测试"
                }
            })
        }
    }
    TabManagerProvider.new().initTabWan(tabLayout, tabNames.size) { curPos: Int, _: Boolean, tvName: TextView ->
        tvName.text = tabNames[curPos]
    }.bindViewPager(viewPager)
}) {

    @SuppressLint("NonConstantResourceId")
    @TabInit(R.layout.tab_wanandroid, "initTabWan")
    val tab: Any? = null
}