package com.wpf.app.quick.demo.viewmodel

import android.view.View
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.model.BindDataTestModel
import com.wpf.app.quick.activity.viewmodel.QuickBindingModel
import com.wpf.app.quickrecyclerview.data.and
import com.wpf.app.quickrecyclerview.utils.SpaceItem
import com.wpf.app.quickutil.base.dp2px

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestModel : QuickBindingModel<ActivityRecyclerviewTestBinding>() {

    override fun onBindingCreated(mViewBinding: ActivityRecyclerviewTestBinding?) {

    }

    fun clean(view: View?) {
        getViewBinding()?.list?.cleanAll()
    }

    fun addMessage(view: View?) {
        getViewBinding()?.list?.addData(BindDataTestModel().and(SpaceItem(10.dp2px(view?.context))))
        getViewBinding()?.list?.getQuickAdapter()?.notifyItemInserted(getViewBinding()?.list?.size() ?: 0)
    }
}