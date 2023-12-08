package com.wpf.app.quick.demo.fragment

import android.annotation.SuppressLint
import android.widget.TextView
import com.wpf.app.quick.activity.QuickVBFragment
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainReleaseBinding
import com.wpf.app.quickbind.annotations.BindSp2View

class MainReleaseFragment: QuickVBFragment<MainReleaseVM, FragmentMainReleaseBinding>(R.layout.fragment_main_release, "正式场") {

    @SuppressLint("NonConstantResourceId")
    @BindSp2View(bindSp = "绑定的SpKey1", defaultValue = "默认值1")
    @BindView(R.id.spTextView1)
    var text1: TextView? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spTextView2)
    var text2: TextView? = null

    @SuppressLint("NonConstantResourceId")
    @BindSp2View(bindSp = "绑定的SpKey3", defaultValue = "默认值3")
    @BindView(R.id.spTextView3)
    var text3: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun initView(viewDataBinding: FragmentMainReleaseBinding?) {
        text1?.postDelayed(
            { text1?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
        text2?.postDelayed(
            { text2?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
        text3?.postDelayed(
            { text3?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
    }
}