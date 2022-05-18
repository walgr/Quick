package com.wpf.app.quick.base.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.lang.reflect.ParameterizedType

/**
 * ViewModel 扩展功能类
 */

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVm0Clazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVm1Clazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VM
}



/**
 *  activity 获取viewModel的扩展函数
 */
fun <T : ViewModel> AppCompatActivity.getViewModel(clazz: Class<T>) =
    ViewModelProvider(this)[clazz]


/**
 *  fragment 获取viewModel的扩展函数
 */
fun <T : ViewModel> Fragment.getViewModel(clazz: Class<T>) =
    ViewModelProvider(this)[clazz]


