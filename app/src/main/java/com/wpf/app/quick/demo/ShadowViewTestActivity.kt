package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import com.wpf.app.quick.activity.QuickBindingActivity
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.databinding.ActivityShadowTestBinding
import com.wpf.app.quickutil.helper.TimeDelayHelper
import com.wpf.app.quickutil.helper.toColor

@GetClass
class ShadowViewTestActivity: QuickBindingActivity<ActivityShadowTestBinding>(
    R.layout.activity_shadow_test,
    titleName = "同步测试页"
) {
    @SuppressLint("SetTextI18n")
    override fun initView(view: ActivityShadowTestBinding) {
        val colorList = arrayOf(
            R.color.black.toColor(requireContext()),
            R.color.colorPrimary.toColor(requireContext()),
            R.color.white.toColor(requireContext())
        )
        var pos = 0
        TimeDelayHelper.registerTimeDelay("1s", 1) {
            view.shadow1.post {
                val index = (pos++) % colorList.size
                view.shadow1.text = (index + 1).toString()
                val newColor = colorList[index]
                view.shadow1.setTextColor(newColor)
                view.shadow1.visibility
            }
        }
        view.shadow6.setOnCheckedChangeListener { _, isChecked ->
            view.shadow8.isVisible = isChecked
        }
    }
}