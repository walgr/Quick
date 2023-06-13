package com.wpf.app.quick.helper

import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.CompoundButton
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.widgets.CheckView
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickrecyclerview.bind.Request2View
import com.wpf.app.quickrecyclerview.interfaces.Request2ViewWithView
import com.wpf.app.quickrecyclerview.interfaces.request2View
import com.wpf.app.quicknetwork.base.BaseResponseI
import com.wpf.app.quicknetwork.call.RealCall
import com.wpf.app.quicknetwork.requestCls
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickRequestData
import com.wpf.app.quickrecyclerview.data.QuickRequestList
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.helper.Request2RefreshView
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickrecyclerview.listeners.request2List
import com.wpf.app.quickutil.LogUtil

/**
 * Created by 王朋飞 on 2022/5/18.
 *
 */

@BindingAdapter(value = ["gotoClass", "parameters"], requireAll = false)
fun <T> View.gotoActivity(gotoClass: Class<T>?, parameters: List<Any>?) {
    LogUtil.e("-----", "clickView:${this}---activityClass:${gotoClass}")
}

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
fun <T> View.request2View(apiCls: Class<T>, methodName: String, parameters: Any?) {
    if (!apiCls.isInterface) return
    if (parameters == null || parameters is RequestData) {
        //列表请求
        if (this is ViewGroup) {
            val refreshView = this.children.toMutableList().flatMap {
                if (it is ViewGroup) it.children.toMutableList() else mutableListOf(it)
            }.find {
                it is RefreshView
            } as? RefreshView
            val parameterTypes: Array<Class<*>>? = parameters?.javaClass?.declaredFields?.filter {
                it.name != "isViewRefresh"
            }?.map {
                it.javaClass
            }?.toTypedArray()
            val method = apiCls.declaredMethods.find {
                it.name == methodName
            } ?: return
            val api: (T.() -> RealCall<BaseResponseI<out QuickRequestList, Any>, Any>) =
                object : (T) -> RealCall<BaseResponseI<out QuickRequestList, Any>, Any> {
                    override fun invoke(p1: T): RealCall<BaseResponseI<out QuickRequestList, Any>, Any> {
                        return method.invoke(
                            p1,
                            *(parameterTypes ?: arrayOf())
                        ) as RealCall<BaseResponseI<out QuickRequestList, Any>, Any>
                    }
                }
            val request2List = request2List { _, callback ->
                requestCls(apiCls) {
                    api()
                }.success {
                    callback.backData(it?.dataI?.returnList())
                }
            } as Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>
            BindData2ViewHelper.bind(refreshView!!, request2List, Request2RefreshView)
        }
    } else if (parameters is List<*>) {
        val parameterTypes: Array<Class<*>> = parameters?.map {
            if (it?.javaClass == Integer::class.java) Int::class.java else it!!.javaClass
        }?.toTypedArray() ?: arrayOf()
        val method = apiCls.getMethod(methodName, *parameterTypes)
        val api: (T.() -> RealCall<BaseResponseI<out QuickRequestData, Any>, Any>) =
            object : (T) -> RealCall<BaseResponseI<out QuickRequestData, Any>, Any> {
                override fun invoke(p1: T): RealCall<BaseResponseI<out QuickRequestData, Any>, Any> {
                    return method.invoke(
                        p1,
                        *(parameters?.toTypedArray() ?: arrayOf())
                    ) as RealCall<BaseResponseI<out QuickRequestData, Any>, Any>
                }
            }
        val request2View = request2View { callback ->
            requestCls(apiCls) {
                api()
            }.success {
                callback.backData(it?.dataI)
            }
        } as Request2ViewWithView<out QuickRequestData, out View>
        BindData2ViewHelper.bind(this, request2View, Request2View)
    }
}

//@Deprecated("暂未开放")
//@BindingAdapter(value = ["apiClass", "methodName", "parameters"], requireAll = false)
//fun <T> View.request2List(apiCls: Class<T>, methodName: String, parameters: List<Any>?) {
//    if (this !is RefreshView) return
//    if (!apiCls.isInterface) return
//    val parameterTypes: Array<Class<*>> = parameters?.map {
//        if (it.javaClass == Integer::class.java) Int::class.java else it.javaClass
//    }?.toTypedArray() ?: arrayOf()
//    val method = apiCls.getMethod(methodName, *parameterTypes)
//    val api: (T.() -> RealCall<BaseResponseI<out QuickItemData, Any>, Any>) =
//        object : (T) -> RealCall<BaseResponseI<out QuickItemData, Any>, Any> {
//            override fun invoke(p1: T): RealCall<BaseResponseI<out QuickItemData, Any>, Any> {
//                return method.invoke(
//                    p1,
//                    *(parameters?.toTypedArray() ?: arrayOf())
//                ) as RealCall<BaseResponseI<out QuickItemData, Any>, Any>
//            }
//        }
//    val request2List = request2List { _, callback ->
//        requestCls(apiCls) {
//            api()
//        }.success {
//            callback.backData(arrayListOf())
//        }
//    } as Request2ListWithView<RequestData, QuickItemData, RefreshView>
//    BindData2ViewHelper.bind(this, request2List, Request2RefreshView)
//}