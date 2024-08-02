package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.wpf.app.quickutil.ability.base.with
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.binding
import com.wpf.app.quickutil.ability.ex.contentView
import com.wpf.app.quickutil.ability.helper.viewGroupCreate
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.databinding.ActivityShadowTestBinding
import com.wpf.app.quickutil.helper.TimeDelayHelper
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickwork.ability.helper.title

@SuppressLint("SetTextI18n")
@GetClass
class ShadowViewTestActivity : QuickActivity(
    contentView<LinearLayout> {
        title("同步测试页")
        viewGroupCreate(layoutId = R.layout.activity_shadow_test)
    }.with(binding<ActivityShadowTestBinding> {
        val colorList = arrayOf(
            R.color.black.toColor(),
            R.color.colorPrimary.toColor(),
            R.color.white.toColor()
        )
        var pos = 0
        TimeDelayHelper.registerTimeDelay("1s", 1) {
            shadow1.post {
                val index = (pos++) % colorList.size
                shadow1.text = (index + 1).toString()
                val newColor = colorList[index]
                shadow1.setTextColor(newColor)
                shadow1.visibility
            }
        }
        shadow6.setOnCheckedChangeListener { _, isChecked ->
            shadow8.isVisible = isChecked
        }
    })
)