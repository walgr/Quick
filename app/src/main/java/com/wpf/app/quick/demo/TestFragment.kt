package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.wpf.app.quick.activity.QuickFragment
import com.wpf.app.quick.activity.QuickViewModelFragment
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.viewmodel.QuickViewModel
import com.wpf.app.quick.widgets.recyclerview.QuickRecyclerView

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class TestFragment: QuickFragment(R.layout.activity_recyclerview_test) {
    @SuppressLint("StaticFieldLeak")
    @BindView(R.id.list)
    var list: QuickRecyclerView? = null

    override fun initView(view: View?) {
        Log.e("TestFragment", "id:${list}---baseview:${view}")
    }

}

class TestViewModel: QuickViewModel<TestFragment>() {

    @SuppressLint("StaticFieldLeak")
    @BindView(R.id.list)
    var list: QuickRecyclerView? = null

    override fun onViewCreated(baseView: TestFragment) {
        Log.e("TestFragment", "id:${list}---baseview:${baseView.view}")
    }

}