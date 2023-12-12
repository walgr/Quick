package com.wpf.app.quick.annotations.bind

import androidx.annotation.IdRes

/**
 * Created by 王朋飞 on 2022/6/13.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class GroupView(@IdRes val idList: IntArray)