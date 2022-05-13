package cn.goodjobs.client.adapterholder

import android.view.View
import android.view.ViewGroup
import cn.goodjobs.client.R
import cn.goodjobs.client.model.TestModel
import com.wpf.app.base.widgets.recyclerview.CommonAdapter
import com.wpf.app.base.widgets.recyclerview.CommonViewHolder

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