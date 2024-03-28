package com.wpf.app.quick.demo

import android.widget.LinearLayout
import android.widget.Toast
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.viewPager2
import com.wpf.app.quickbind.viewpager2.ViewPagerHelper
import com.wpf.app.quickutil.helper.postDelay
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwork.ability.title

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class ViewPagerTestActivity : QuickActivity(
    contentView<LinearLayout> {
        title("ViewPager刷新测试")
        viewPager2<TestFragment>(it, defaultSize = 10) {
            setCurrentItem(5, false)
            Toast.makeText(it.forceTo(), "3秒后刷新", Toast.LENGTH_SHORT).show()
            postDelay(3000) {
                ViewPagerHelper.notifyPagerSize(this, 20)
                setCurrentItem(15, false)
            }
        }
    })