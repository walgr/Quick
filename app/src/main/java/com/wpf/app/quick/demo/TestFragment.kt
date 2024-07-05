package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wpf.app.quick.ability.QuickFragment
import com.wpf.app.quickutil.ability.ex.contentView
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickutil.helper.generic.printLog

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class TestFragment : QuickFragment(
    contentView(R.layout.fragment_test_viewpager2)
) {
    @SuppressLint("StaticFieldLeak", "NonConstantResourceId")
    @BindView(R.id.title)
    var btnClean: TextView? = null

    @AutoGet
    val pos: Int = 0

    @SuppressLint("SetTextI18n")
    override fun initView(view: View) {
        super.initView(view)
        btnClean?.text = "Fragment${pos}"
        pos.printLog("当前:")
    }

    override fun getInitBundle(activity: Activity?, position: Int): Bundle {
        return Bundle().apply {
            if (activity is ViewPagerNotifyDataTestActivity) {
                putInt("pos", activity.fragmentData[position])
            } else {
                putInt("pos", position)
            }
        }
    }
}