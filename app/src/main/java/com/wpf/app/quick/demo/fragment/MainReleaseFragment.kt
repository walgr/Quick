package com.wpf.app.quick.demo.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.wpf.app.quick.ability.QuickFragment
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.modelBinding
import com.wpf.app.base.ability.base.with
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainReleaseBinding
import com.wpf.app.quickbind.annotations.BindSp2View
import com.wpf.app.quickutil.helper.postDelay

class MainReleaseFragment : QuickFragment(
    contentView(R.layout.fragment_main_release).with(modelBinding<MainReleaseVM, FragmentMainReleaseBinding>())
) {

    @SuppressLint("NonConstantResourceId")
    @BindSp2View(bindSp = "绑定的SpKey1", defaultValue = "默认值1")
    @BindView(R.id.spTextView1)
    var text1: TextView? = null

    @SuppressLint("NonConstantResourceId")
    @BindSp2View(bindSp = "绑定的SpKey2", defaultValue = "默认值2")
    @BindView(R.id.spTextView2)
    var text2: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun initView(view: View) {
        super.initView(view)
        text1?.postDelay(1000) { text1?.text = System.currentTimeMillis().toString() + "" }
        text2?.postDelay(1000) { text2?.text = System.currentTimeMillis().toString() + "" }
    }
}