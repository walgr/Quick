package com.wpf.app.quick.viewmodel

import android.view.View
import com.wpf.app.quick.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.model.BindDataTestModel

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