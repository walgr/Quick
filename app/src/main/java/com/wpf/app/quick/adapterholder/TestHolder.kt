package com.wpf.app.quick.adapterholder

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quick.R
import com.wpf.app.quick.model.TestModel
import com.wpf.app.quick.base.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.base.widgets.recyclerview.QuickViewHolder

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */

class TestHolder(mParent: ViewGroup) : QuickViewHolder<TestModel>(mParent, layoutId = R.layout.holder_test) {

    override fun onCreateViewHolder(itemView: View) {

    }

    override fun onBindViewHolder(
        adapter: QuickAdapter,
        data: TestModel,
        position: Int
    ) {

    }

}