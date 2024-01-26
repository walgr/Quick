package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.wpf.app.quick.activity.QuickBindingActivity
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.annotations.tab.IdView
import com.wpf.app.quick.annotations.tab.TabInit
import com.wpf.app.quick.annotations.tab.view.ViewType
import com.wpf.app.quick.demo.databinding.ActivityMainCodeBinding
import com.wpf.app.quick.demo.fragment.MainReleaseFragment
import com.wpf.app.quick.demo.fragment.MainTestFragment
import com.wpf.app.quickwidget.tab.TabManager
import com.wpf.app.quickbind.annotations.BindFragments
import com.wpf.app.quickbind.viewpager.QuickViewPager
import com.wpf.app.quickutil.helper.toColor
import com.wpf.quick.helper.initTabMain

class CodeMainActivity :
    QuickBindingActivity<ActivityMainCodeBinding>(R.layout.activity_main_code, titleName = "快捷") {

    @SuppressLint("NonConstantResourceId")
    @TabInit(
        R.layout.tab_main,
        funName = "initTabMain",
        initIdList = [
            IdView(R.id.tvName, ViewType.TextView),
            IdView(R.id.ivIcon, ViewType.ImageView),
        ]
    )
    private val tabs: TabManager = TabManager()

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewPager)
    @BindFragments(
        fragments = [MainReleaseFragment::class, MainTestFragment::class],
        withState = false
    )
    var viewPager: QuickViewPager? = null
    override fun initView(view: ActivityMainCodeBinding?) {
        super.initView(view)
        val tabs = arrayOf(
            Pair(R.drawable.ic_home, "正式场"),
            Pair(R.drawable.ic_test, "测试场")
        )
        this.tabs.initTabMain(
            parent = view?.bottomTabs,
            size = tabs.size,
            repeatClick = false
        ) { curPos: Int, isSelect: Boolean, tvName: TextView, ivIcon: ImageView ->
            tvName.text = tabs[curPos].second
            ivIcon.setImageResource(tabs[curPos].first)
            tvName.setTextColor((if (isSelect) R.color.colorPrimary else R.color.black).toColor(this))
        }.bindViewPager(viewPager)

    }
}