package cn.goodjobs.client.common

import androidx.lifecycle.ViewModel

/**
 * Created by 王朋飞 on 2022/4/2.
 * Activity基础控制器
 */
open class BaseViewModel<T : BaseView>(baseView: T) : ViewModel() {

}