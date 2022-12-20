package com.wpf.app.quick.demo.viewmodel

import android.view.View
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.model.BindDataTestModel
import com.wpf.app.quick.activity.viewmodel.QuickBindingModel
import com.wpf.app.quick.demo.R
import com.wpf.app.quickrecyclerview.data.and
import com.wpf.app.quickrecyclerview.utils.LineItem
import com.wpf.app.quickrecyclerview.utils.SpaceItem
import com.wpf.app.quickutil.base.dp2px
import com.wpf.app.quickutil.base.toColor

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
        getViewBinding()?.list?.addData(
            BindDataTestModel().and(
                LineItem(
                    10.dp2px(view?.context),
                    color = R.color.colorPrimary.toColor(getContext()!!)
                )
            )
        )
        getViewBinding()?.list?.getQuickAdapter()
            ?.notifyItemInserted(getViewBinding()?.list?.size() ?: 0)
    }
}