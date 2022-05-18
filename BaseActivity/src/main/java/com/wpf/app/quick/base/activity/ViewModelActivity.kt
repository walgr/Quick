package com.wpf.app.quick.base.activity

import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.base.helper.getVm0Clazz
import com.wpf.app.quick.base.viewmodel.BaseViewModel


/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 */
abstract class ViewModelActivity<T: BaseViewModel<H>, H: BaseView>
    @JvmOverloads constructor(
    private var viewModel: T? = null
) : BaseActivity() {

    override fun dealContentView() {
        viewModel = ViewModelProvider(this)[getVm0Clazz(this)]
        viewModel?.onModelCreate(this as H)
    }
}