package com.wpf.app.quick.demo.viewmodel

import android.view.View
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.model.BindDataTestModel
import com.wpf.app.quick.viewmodel.BindingViewModel

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestViewModel : BindingViewModel<ActivityRecyclerviewTestBinding>() {

    override fun onBindingCreated(mViewBinding: ActivityRecyclerviewTestBinding?) {

    }

    fun clean(view: View?) {
        getViewBinding()?.list?.getQuickAdapter()?.cleanAll()
    }

    fun addMessage(view: View?) {
        getViewBinding()?.list?.getQuickAdapter()?.addData(BindDataTestModel())
        getViewBinding()?.list?.getQuickAdapter()?.notifyDataSetChanged()
    }
}