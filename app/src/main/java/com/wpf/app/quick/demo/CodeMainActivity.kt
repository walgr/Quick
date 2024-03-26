package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.binding
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.with
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.annotations.tab.TabInit
import com.wpf.app.quick.demo.databinding.ActivityMainCodeBinding
import com.wpf.app.quick.demo.fragment.MainReleaseFragment
import com.wpf.app.quick.demo.fragment.MainTestFragment
import com.wpf.app.quickbind.annotations.BindFragments
import com.wpf.app.quickbind.viewpager.QuickViewPager
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.widget.onPageSelected
import com.wpf.app.quickwidget.tab.TabManagerProvider
import com.wpf.quick.helper.initTabMain

class CodeMainActivity : QuickActivity(
    contentView(R.layout.activity_main_code).with(binding<ActivityMainCodeBinding> {
        val tabs = arrayOf(
            "正式场" to R.drawable.ic_home,
            "测试场" to R.drawable.ic_test,
        )
        TabManagerProvider.new().initTabMain(
            parent = bottomTabs, size = tabs.size, repeatClick = false
        ) { curPos: Int, isSelect: Boolean, ivIcon: ImageView, tvName: TextView ->
            tvName.text = tabs[curPos].first
            ivIcon.setImageResource(tabs[curPos].second)
            tvName.setTextColor((if (isSelect) R.color.colorPrimary else R.color.black).toColor())
        }.bindViewPager(viewPager)

        viewPager.onPageSelected {
            titleView.setTitle(tabs[it].first)
        }
    })
) {

    @SuppressLint("NonConstantResourceId")
    @TabInit(layoutId = R.layout.tab_main, funName = "initTabMain")
    @BindView(R.id.viewPager)
    @BindFragments(
        fragments = [MainReleaseFragment::class, MainTestFragment::class], withState = false
    )
    var viewPager: QuickViewPager? = null
}