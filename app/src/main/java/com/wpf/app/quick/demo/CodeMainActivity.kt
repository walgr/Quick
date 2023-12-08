package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.wpf.app.quick.activity.QuickBindingActivity
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.annotations.IdView
import com.wpf.app.quick.annotations.TabInit
import com.wpf.app.quick.annotations.view.ViewType
import com.wpf.app.quick.demo.databinding.ActivityMainCodeBinding
import com.wpf.app.quick.demo.fragment.MainReleaseFragment
import com.wpf.app.quick.demo.fragment.MainTestFragment
import com.wpf.app.quick.helper.tab.TabManager
import com.wpf.app.quickbind.annotations.BindFragments
import com.wpf.app.quickbind.viewpager.QuickViewPager
import com.wpf.app.quickutil.LogUtil
import com.wpf.app.quickutil.base.toColor

class CodeMainActivity :
    QuickBindingActivity<ActivityMainCodeBinding>(R.layout.activity_main_code, titleName = "快捷") {

    @SuppressLint("NonConstantResourceId")
    @TabInit(
        R.layout.tab_main,
        funName = "initTabMain",
        initIdList = [IdView(R.id.tvName, ViewType.TextView)]
    )
    private val tabs: TabManager = TabManager()

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewPager)
    @BindFragments(fragments = [MainTestFragment::class, MainReleaseFragment::class], withState = false)
    var viewPager: QuickViewPager? = null
    override fun initView(view: ActivityMainCodeBinding?) {
        super.initView(view)
        val tabName = arrayOf("测试场", "正式场")
        viewPager?.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabs.posChange(position)
            }
        })
        tabs.posChange {
            LogUtil.e("当前选择$it")
            viewPager?.currentItem = it
        }.initTabMain(parent = view?.bottomTabs, size = tabName.size, repeatClick = false) { curPos: Int, isSelect: Boolean, tvName: TextView ->
            tvName.text = tabName[curPos]
            tvName.setTextColor((if (isSelect) R.color.colorPrimary else R.color.black).toColor(this))
        }
    }
}