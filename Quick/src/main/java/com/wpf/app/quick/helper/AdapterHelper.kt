package com.wpf.app.quick.helper

import android.view.View
import android.widget.Checkable
import android.widget.CompoundButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.widgets.CheckView
import com.wpf.app.quicknetwork.base.BaseResponseIA
import com.wpf.app.quicknetwork.base.WpfRequest
import com.wpf.app.quicknetwork.call.RealCall
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper
import com.wpf.app.quicknetwork.request
import com.wpf.app.quicknetwork.requestCls
import com.wpf.app.quickutil.LogUtil
import kotlin.jvm.functions.FunctionN

/**
 * Created by 王朋飞 on 2022/5/18.
 *
 */

@BindingAdapter("isSelect")
fun isSelect(checkView: View, select: Boolean) {
    if (checkView is Checkable) {
        checkView.isChecked = select
    }
}

@BindingAdapter("bindSelect")
fun bindSelect(checkView: View, select: MutableLiveData<Boolean>) {
    isSelect(checkView, select.value ?: false)
    if (checkView is LifecycleOwner) {
        select.removeObservers(checkView)
        select.observe(checkView) {
            isSelect(checkView, select.value ?: false)
        }
    }
    if (checkView is CheckView) {
        onViewCheck(checkView) { _, isChecked ->
            run {
                select.postValue(isChecked)
            }
        }
    }
}

@BindingAdapter("onViewClick")
fun onViewClick(view: View, onClick: View.OnClickListener) {
    view.setOnClickListener(onClick)
}

@BindingAdapter("onViewCheck")
fun onViewCheck(view: View, onChange: CompoundButton.OnCheckedChangeListener?) {
    if (view is CheckView) {
        view.setOnCheckedChangeListener(onChange)
    }
}

@BindingAdapter(value = ["apiClass", "methodName"], requireAll = true)
fun <T> View.request2This(apiCls: Class<T>, methodName: String) {
//    if (api == null) return
//    val apiCls = api!!::class.java
//    val apiCls = apiCls!!::class.java
    if (!apiCls.isInterface) return
    val method = apiCls.getMethod(methodName, Int::class.java)
    val fun1: (T.()-> RealCall<BaseResponseIA<Any>, Any>) = object : (T) -> RealCall<BaseResponseIA<Any>, Any> {
        override fun invoke(p1: T): RealCall<BaseResponseIA<Any>, Any> {
            return method.invoke(p1, 1) as RealCall<BaseResponseIA<Any>, Any>
        }
    }
    RetrofitCreateHelper.getServiceT(apiCls).run(fun1).enqueue(WpfRequest()).success {
        LogUtil.e("请求-${it.toString()}")
    }.fail {

    }.after { LogUtil.e("请求结束") }
}