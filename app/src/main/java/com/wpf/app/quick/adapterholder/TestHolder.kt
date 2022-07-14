package com.wpf.app.quick.adapterholder

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import com.wpf.app.quick.R
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.model.TestModel
import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.QuickViewHolder

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class TestHolder(mParent: ViewGroup) :
    QuickViewHolder<TestModel>(mParent, R.layout.holder_test, true) {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt)
    var txt: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(adapter: QuickAdapter, data: TestModel?, position: Int) {
        txt?.postDelayed(
            { txt?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
    }
}