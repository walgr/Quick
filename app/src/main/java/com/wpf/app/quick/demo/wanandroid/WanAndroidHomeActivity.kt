package com.wpf.app.quick.demo.wanandroid

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.tabs.TabLayout
import com.wpf.app.base.ability.scope.viewGroupApply
import com.wpf.app.base.ability.scope.withViewGroupScope
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.helper.coordinator
import com.wpf.app.quick.ability.helper.fragment
import com.wpf.app.quick.ability.helper.gravity
import com.wpf.app.quick.ability.helper.myLayout
import com.wpf.app.quick.ability.helper.viewFragment
import com.wpf.app.quick.ability.helper.viewPagerBuilder
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.annotations.tab.TabInit
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.wanandroid.fragment.RecommendFragment
import com.wpf.app.quick.helper.getActivity
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.onClick
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickwidget.tab.TabManagerProvider
import com.wpf.app.quickwidget.title.ability.backClick
import com.wpf.app.quickwork.ability.helper.dialog
import com.wpf.app.quickwork.ability.helper.moreGroup
import com.wpf.app.quickwork.ability.helper.tabLayout
import com.wpf.app.quickwork.ability.helper.text
import com.wpf.app.quickwork.ability.helper.title

@GetClass
class WanAndroidHomeActivity : QuickActivity(contentView<LinearLayout> {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    title {
        moreGroup(8.dp) {
            withViewGroupScope {
                text(text = "登录", textColor = R.color.white.toColor(), textSize = 16f.dp) {
                    onClick {
                        dialog(layoutViewCreate = {
                            text(
                                text = "弹窗",
                                textColor = R.color.white.toColor(),
                                textSize = 24f.dp
                            )
                        }).show()
                    }
                }
            }
        }
        backClick {
            this@contentView.self.getActivity().apply {
                setResult(RESULT_OK)
                finish()
            }
        }
    }
    coordinator(followSlideLayout = {
        withViewGroupScope {
            text(
                layoutParams = matchWrapLayoutParams(),
                text = "Wan Android",
                textColor = R.color.white.toColor(),
                textSize = 24f.dp,
                textGravity = Gravity.CENTER
            ) {
                setPadding(32.dp, 16.dp, 32.dp, 16.dp)
            }
        }
    }, scrollFlags = SCROLL_FLAG_SCROLL, topSuspendLayout = {
        withViewGroupScope {
            tabLayout(layoutParams = matchWrapLayoutParams().reset(height = 44.dp))
        }
    }, bottomScrollLayout = {
        withViewGroupScope {
            viewPagerBuilder(quickView = this@contentView.self) {
                fragment(RecommendFragment())
                viewFragment {
                    myLayout<NestedScrollView>(layoutParams = matchLayoutParams()) {
                        viewGroupApply {
                            isFillViewport = true
                        }
                        myLayout<LinearLayout>(layoutParams = matchLayoutParams()) {
                            text(text = "测试", textSize = 24f.dp)
                        }.gravity(Gravity.CENTER)
                    }
                }
            }
        }
    }) { _, topSuspendLayout, bottomScrollLayout ->
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