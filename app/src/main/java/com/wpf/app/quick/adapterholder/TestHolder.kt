package com.wpf.app.quick.adapterholder

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quick.R
import com.wpf.app.quick.model.TestModel
import com.wpf.app.quick.base.widgets.recyclerview.CommonAdapter
import com.wpf.app.quick.base.widgets.recyclerview.CommonViewHolder

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */

class TestHolder(mParent: ViewGroup) : CommonViewHolder<TestModel>(mParent, layoutId = R.layout.holder_test) {

    override fun bindViewBinding(view: View) {

    }

    override fun onBindViewHolder(
        adapter: CommonAdapter,
        data: TestModel,
        position: Int
    ) {

    }

}