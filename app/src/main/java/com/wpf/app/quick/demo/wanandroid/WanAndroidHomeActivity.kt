package com.wpf.app.quick.demo.wanandroid

import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
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
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickwidget.tab.TabManagerProvider
import com.wpf.app.quickwork.ability.tabLayout
import com.wpf.app.quickwork.ability.textButton
import com.wpf.app.quickwork.ability.title

@GetClass
class WanAndroidHomeActivity : QuickActivity(contentView<LinearLayout> { quickView ->
    var viewPager: ViewPager? = null
    title("WanAndroid") {
        textButton("登录", {

        })
    }
    val tabNames = arrayOf("推荐", "专题")
    val tabLayout = tabLayout(layoutParams = matchWrapLayoutParams.reset(height = 44.dp()))
    viewPager = viewPager(quickView) {
        fragment(RecommendFragment())
        viewFragment {
            myLayout(layoutViewInContext = runOnContext {
                TextView(it).apply {
                    text = "测试"
                }
            })
        }
    }
    TabManagerProvider.new().initTabWan(tabLayout, tabNames.size) { curPos: Int, isSelect: Boolean, tvName: TextView ->
        tvName.text = tabNames[curPos]
    }.bindViewPager(viewPager)
}) {

    @TabInit(R.layout.tab_wanandroid, "initTabWan")
    val tab: Any? = null
}