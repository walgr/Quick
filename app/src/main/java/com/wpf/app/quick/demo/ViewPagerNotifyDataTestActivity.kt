package com.wpf.app.quick.demo

import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.helper.viewPager
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quickutil.ability.ex.contentViewWithSelf
import com.wpf.app.quickwork.ability.helper.smartRefreshLayout
import com.wpf.app.quickwork.ability.helper.title
import kotlin.random.Random

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
@GetClass
class ViewPagerNotifyDataTestActivity : QuickActivity(
    contentViewWithSelf<ViewPagerNotifyDataTestActivity, LinearLayout> {
        title("ViewPager刷新测试")
        smartRefreshLayout(autoRefresh = false, enableLoadMore = false, refreshListener = { viewPager ->
            this@contentViewWithSelf.self.fragmentData = mutableListOf<Int>().apply {
                val start = Random.nextInt(10) * 10
                repeat(10) {
                    add((start + it))
                }
            }
            viewPager.adapter?.notifyDataSetChanged()
            finishRefresh()
        }, contentBuilder = {
            viewPager<TestFragment>(
                quick = this@contentViewWithSelf.self,
                defaultSize = this@contentViewWithSelf.self.fragmentData.size,
                isLoop = true
            )
        })
    }) {

    var fragmentData = mutableListOf<Int>().apply {
        val start = 0
        repeat(10) {
            add((start + it))
        }
    }
}