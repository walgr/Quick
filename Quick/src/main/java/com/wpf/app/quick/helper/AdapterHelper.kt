package com.wpf.app.quick.helper

import android.view.View
import android.widget.Checkable
import android.widget.CompoundButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.widgets.CheckView
import com.wpf.app.quickbind.bindview.QuickRequestData
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.Request2View
import com.wpf.app.quickbind.interfaces.Request2ViewWithView
import com.wpf.app.quickbind.interfaces.request2View
import com.wpf.app.quicknetwork.base.BaseResponseI
import com.wpf.app.quicknetwork.call.RealCall
import com.wpf.app.quicknetwork.requestCls
import com.wpf.app.quickutil.LogUtil
import java.lang.reflect.Type

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

@BindingAdapter(value = ["apiClass", "methodName", "parameters"], requireAll = false)
fun <T> View.request2This(apiCls: Class<T>, methodName: String, parameters: List<Any>?) {
    if (!apiCls.isInterface) return
    val parameterTypes : Array<Class<*>> = parameters?.map {
        if (it.javaClass == Integer::class.java) Int::class.java else it.javaClass
    }?.toTypedArray() ?: arrayOf()
    val method = apiCls.getMethod(methodName, *parameterTypes)
    val api: (T.() -> RealCall<BaseResponseI<out QuickRequestData, Any>, Any>) = object : (T) -> RealCall<BaseResponseI<out QuickRequestData, Any>, Any> {
        override fun invoke(p1: T): RealCall<BaseResponseI<out QuickRequestData, Any>, Any> {
            return method.invoke(p1, *(parameters?.toTypedArray() ?: arrayOf())) as RealCall<BaseResponseI<out QuickRequestData, Any>, Any>
        }
    }
    val request2View = request2View { callback ->
        requestCls(apiCls) {
            api()
        }.success {
            callback.backData(it?.dataI)
        }.fail {
            LogUtil.e("请求出错-${it.toString()}")
        }
    } as Request2ViewWithView<out QuickRequestData, out View>
    BindData2ViewHelper.bind(this, request2View, Request2View)
}