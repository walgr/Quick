package com.wpf.app.quick.demo

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.wpf.app.base.ability.ex.contentViewWithSelf
import com.wpf.app.base.ability.helper.viewGroupCreate
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.padding
import com.wpf.app.quickutil.helper.weight
import com.wpf.app.quickwidget.wheel.WheelItemData
import com.wpf.app.quickwidget.wheel.WheelView
import com.wpf.app.quickwork.ability.helper.title

class WheelViewTestActivity: QuickActivity(
    contentViewWithSelf<WheelViewTestActivity, LinearLayout> {
        title("WheelView测试")
        viewGroupCreate<LinearLayout> {
            view.padding(16.dp)
            view.gravity = Gravity.CENTER
            self.wheelView = WheelView(context).apply {

            }
            addView(self.wheelView!!, matchWrapMarginLayoutParams())
        }.weight(1f)
    }
) {

    private var wheelView: WheelView? = null

    override fun initView(view: View) {
        super.initView(view)
        val testData: MutableList<WheelItemData> = mutableListOf()
        repeat(50) {
            testData.add(WheelItemData(it.toString(), System.currentTimeMillis().toString()))
        }
        wheelView?.setData(testData)
    }
}