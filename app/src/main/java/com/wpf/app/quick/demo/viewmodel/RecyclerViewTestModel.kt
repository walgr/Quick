package com.wpf.app.quick.demo.viewmodel

import android.view.View
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.model.BindDataTestModel
import com.wpf.app.quick.activity.viewmodel.QuickVBModel

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestModel : QuickVBModel<ActivityRecyclerviewTestBinding>() {

    override fun onBindingCreated(view: ActivityRecyclerviewTestBinding?) {
    }

    fun clean(view: View?) {
        getViewBinding()?.list?.cleanAll()
    }

    fun addMessage(view: View?) {
        getViewBinding()?.list?.addData(
            BindDataTestModel()
        )
        getViewBinding()?.list?.getQuickAdapter()?.notifyDataSetChanged()
    }
}