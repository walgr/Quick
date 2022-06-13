package com.wpf.app.quick.base.activity

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.base.helper.QuickBindHelper
import com.wpf.app.quick.base.helper.getVm0Clazz
import com.wpf.app.quick.base.viewmodel.BaseViewModel


/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 */
abstract class ViewModelActivity<T: BaseViewModel<H>, H: BaseView>
    @JvmOverloads constructor(
        @LayoutRes override val layoutId: Int,
        private var viewModel: T? = null,
        activityTitle: String = "",
) : BaseActivity(layoutId = layoutId, activityTitle = activityTitle) {

    override fun dealContentView() {
        viewModel = ViewModelProvider(this)[getVm0Clazz(this)?:return]
        QuickBindHelper.bind(this, viewModel)
        viewModel?.onModelCreate(this as H)
    }
}