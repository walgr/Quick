package com.wpf.app.quick.demo

import android.widget.LinearLayout
import android.widget.Toast
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentViewWithSelf
import com.wpf.app.quick.ability.helper.viewPager
import com.wpf.app.quickbind.viewpager2.ViewPagerHelper
import com.wpf.app.quickutil.helper.postDelay
import com.wpf.app.quickwork.ability.helper.title
import kotlin.random.Random

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class ViewPagerNotifySizeTestActivity : QuickActivity(
    contentViewWithSelf<ViewPagerNotifySizeTestActivity, LinearLayout> {
        title("ViewPager刷新测试")
        viewPager<TestFragment>(
            quickView = self,
            defaultSize = self.fragmentData.size,
            isLoop = false
        ) {
            setCurrentItem(5, false)
            Toast.makeText(self, "3秒后刷新", Toast.LENGTH_SHORT).show()
            postDelay(3000) {
                self.fragmentData.addAll(mutableListOf<Int>().apply {
                    val start = self.fragmentData.last() + 1
                    repeat(10) {
                        add((start + it))
                    }
                })
                ViewPagerHelper.notifyPagerSize(this, self.fragmentData.size)
                setCurrentItem(15, false)
            }
        }
    }) {

    private val fragmentData = mutableListOf<Int>().apply {
        val start = Random.nextInt(10) * 10
        repeat(10) {
            add((start + it))
        }
    }
}