package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wpf.app.quick.activity.QuickFragment
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quickbind.annotations.AutoGet

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class TestFragment: QuickFragment(R.layout.fragment_test_viewpager2) {
    @SuppressLint("StaticFieldLeak", "NonConstantResourceId")
    @BindView(R.id.title)
    var btnClean: TextView? = null

    @AutoGet
    val pos: Int = 0

    @SuppressLint("SetTextI18n")
    override fun initView(view: View?) {
        btnClean?.text = "Fragment${pos}"
    }

    override fun getInitBundle(activity: Activity?, position: Int): Bundle? {
        return Bundle().apply {
            putInt("pos", position)
        }
    }
}