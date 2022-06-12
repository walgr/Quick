package com.wpf.app.quick.viewmodel

import android.view.View
import android.view.animation.OvershootInterpolator
import com.wpf.app.quick.base.utils.isScrollBottom
import com.wpf.app.quick.base.viewmodel.BindingViewModel
import com.wpf.app.quick.databinding.ActivityMainBinding
import com.wpf.app.quick.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.model.MyMessage
import com.wpf.app.quick.model.TestModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

/**
 * Created by 王朋飞 on 2022/5/10.
 *
 */
class RecyclerViewTestViewModel : BindingViewModel<ActivityRecyclerviewTestBinding>() {

    override fun onBindingCreate(viewBinding: ActivityRecyclerviewTestBinding?) {
        super.onBindingCreate(viewBinding)
        viewBinding?.list?.itemAnimator = SlideInUpAnimator(OvershootInterpolator()).also {
            it.addDuration = 50
        }
    }

    fun clean(view: View) {
        viewBinding?.list?.mAdapter?.cleanAll()
    }

    fun addMessage(view: View) {
        viewBinding?.list?.mAdapter?.addData(TestModel())
        viewBinding?.list?.mAdapter?.notifyItemInserted(viewBinding?.list?.size() ?: 0)
        if (viewBinding?.list?.isScrollBottom() == true) {
            viewBinding?.list?.smoothScrollToPosition(viewBinding?.list?.size() ?: 0)
        }
    }

    fun addOtherCome(view: View) {

    }
}