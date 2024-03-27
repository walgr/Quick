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
import com.wpf.app.quickutil.widget.onPageSelected
import com.wpf.app.quickutil.widget.onTabSelected
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
    tabLayout(R.layout.tab_wanandroid, 2,
        matchWrapLayoutParams.reset(height = 44.dp()),
        tabInit = { position, tabView ->
            val tab = tabView.findViewById<TextView>(R.id.tvName)
            when (position) {
                0 -> {
                    tab.text = "推荐"
                }

                1 -> {
                    tab.text = "专题"
                }
            }
        }) {
        onTabSelected {
            viewPager?.setCurrentItem(it)
        }
        post {
            viewPager?.onPageSelected {
                selectTab(getTabAt(it))
            }
        }
    }
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
}) {

    @TabInit(R.layout.tab_wanandroid, "initTabWan")
    val tab: Any? = null
}