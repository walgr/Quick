package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.wpf.app.quick.activity.QuickFragment
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickrecyclerview.QuickRecyclerView

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class TestFragment: QuickFragment(R.layout.activity_recyclerview_test) {
    @SuppressLint("StaticFieldLeak", "NonConstantResourceId")
    @BindView(R.id.btnClean)
    var btnClean: TextView? = null

    override fun initView(view: View?) {
        btnClean?.text = "123123"
    }

}

class TestViewModel: QuickViewModel<TestFragment>() {

    @SuppressLint("StaticFieldLeak", "NonConstantResourceId")
    @BindView(R.id.list)
    var list: QuickRecyclerView? = null

    override fun onViewCreated(baseView: TestFragment) {

    }

}