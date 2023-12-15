package com.wpf.app.quick.demo.fragment

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import androidx.core.view.isVisible
import com.wpf.app.quick.activity.QuickVBFragment
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainTestBinding
import com.wpf.app.quickutil.LogUtil
import com.wpf.app.quickutil.TimeDelayHelper
import com.wpf.app.quickutil.other.toColor
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class MainTestFragment : QuickVBFragment<MainTestVM, FragmentMainTestBinding>(
    R.layout.fragment_main_test,
    titleName = "测试场"
) {
    @SuppressLint("SetTextI18n")
    override fun initView(view: FragmentMainTestBinding?) {
        val colorList = arrayOf(
            R.color.black.toColor(requireContext()),
            R.color.colorPrimary.toColor(requireContext()),
            R.color.white.toColor(requireContext())
        )
        var pos = 0
        TimeDelayHelper.registerTimeDelay("1s", 1) {
            view?.shadow1?.post {
                val index = (pos++) % colorList.size
                view.shadow1.text = (index + 1).toString()
                val newColor = colorList[index]
                view.shadow1.setTextColor(newColor)
                view.shadow1.visibility
            }
        }
        view?.shadow6?.setOnCheckedChangeListener { _, isChecked ->
            view.shadow8.isVisible = isChecked
        }
    }
}