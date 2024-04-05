package com.wpf.app.quick.demo.wanandroid

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.tabs.TabLayout
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.coordinator
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
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickwidget.tab.TabManagerProvider
import com.wpf.app.quickwork.ability.tabLayout
import com.wpf.app.quickwork.ability.textButton
import com.wpf.app.quickwork.ability.title
import com.wpf.app.quickwork.widget.QuickTitleView

@GetClass
class WanAndroidHomeActivity : QuickActivity(contentView<LinearLayout> { quickView ->
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    title {
        textButton("登录", {

        })
        setCommonClickListener(object : QuickTitleView.CommonClickListener {
            override fun onBackClick(view: View) {
                super.onBackClick(view)
                quickView.getActivity().setResult(RESULT_OK)
            }
        })
    }
    coordinator(
        followSlideLayout = {
            TextView(context).apply {
                layoutParams = matchWrapLayoutParams()
                text = "Wan Android"
                textSize = 24.dpF()
                gravity = Gravity.CENTER
                setTextColor(R.color.white.toColor())
                setPadding(32.dp(), 16.dp(), 32.dp(), 32.dp())
            }
        },
        scrollFlags = SCROLL_FLAG_SCROLL,
        topSuspendLayout = {
            tabLayout(layoutParams = matchWrapLayoutParams().reset(height = 44.dp()))
        },
        bottomScrollLayout = {
            viewPager(quickView = quickView) {
                fragment(RecommendFragment())
                viewFragment {
                    myLayout(layoutViewInContext = runOnContext {
                        NestedScrollView(it).apply {
                            addView(TextView(it).apply {
                                text = "测试"
                            })
                        }
                    })
                }
            }
        }
    ) { _, topSuspendLayout, bottomScrollLayout ->
        tabLayout = topSuspendLayout
        viewPager = bottomScrollLayout
    }
    val tabNames = arrayOf("推荐", "专题")
    TabManagerProvider.new()
        .initTabWan(tabLayout, tabNames.size) { curPos: Int, _: Boolean, tvName: TextView ->
            tvName.text = tabNames[curPos]
        }.bindViewPager(viewPager)
}) {

    @SuppressLint("NonConstantResourceId")
    @TabInit(R.layout.tab_wanandroid, "initTabWan")
    val tab: Any? = null
}