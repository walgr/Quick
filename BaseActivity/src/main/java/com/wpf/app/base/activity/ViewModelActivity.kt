package com.wpf.app.base.activity


/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 */
abstract class ViewModelActivity<T: BaseViewModel<H>, H: BaseView>
    @JvmOverloads constructor(
    private val viewModel: T? = null
) : BaseActivity() {

}