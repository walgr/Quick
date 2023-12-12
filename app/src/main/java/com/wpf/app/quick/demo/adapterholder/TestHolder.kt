package com.wpf.app.quick.demo.adapterholder

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.model.TestModel
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class TestHolder(mParent: ViewGroup) :
    QuickViewHolder<TestModel>(mParent, R.layout.holder_test, dealBindView = true) {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt)
    var txt: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(adapter: QuickAdapter, data: TestModel?, position: Int) {
        super.onBindViewHolder(adapter, data, position)
        txt?.postDelayed(
            { txt?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
    }
}