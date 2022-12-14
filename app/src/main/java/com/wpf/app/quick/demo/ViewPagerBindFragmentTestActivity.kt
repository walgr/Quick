package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quickbind.annotations.BindFragment2
import com.wpf.app.quickbind.viewpager2.ViewPager2Helper

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class ViewPagerBindFragmentTestActivity : QuickActivity(R.layout.activity_test_viewpager) {

    @SuppressLint("NonConstantResourceId")
    @BindFragment2(fragment = TestFragment::class, defaultSize = 10)
    @BindView(R.id.viewPager)
    val viewPager: ViewPager2? = null

    override fun initView() {
        viewPager?.setCurrentItem(5, false)
        viewPager?.adapter?.notifyDataSetChanged()
        Toast.makeText(this, "3秒后刷新", Toast.LENGTH_SHORT).show()
        viewPager?.postDelayed({
            ViewPager2Helper.notifyPagerSize(viewPager, 20)
            viewPager.setCurrentItem(15, false)
        }, 3000)
    }
}