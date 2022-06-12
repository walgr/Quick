package com.wpf.app.quick.adapterholder

import android.view.ViewGroup
import android.widget.TextView
import com.wpf.app.quick.R
import com.wpf.app.quick.base.helper.FindView
import com.wpf.app.quick.model.TestModel
import com.wpf.app.quick.base.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.base.widgets.recyclerview.QuickViewHolder

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */

class TestHolder(mParent: ViewGroup) :
    QuickViewHolder<TestModel>(mParent, layoutId = R.layout.holder_test, dealBindView = true) {

    @FindView(R.id.txt)
    val txt: TextView? = null

    override fun onBindViewHolder(
        adapter: QuickAdapter,
        data: TestModel,
        position: Int
    ) {
        txt?.postDelayed({
            txt.text = System.currentTimeMillis().toString()
        }, 1000)
    }

}