package com.wpf.app.quick.helper

import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.CompoundButton
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
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickRequestData
import com.wpf.app.quickrecyclerview.data.QuickRequestList
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.helper.Request2RefreshView
import com.wpf.app.quickrecyclerview.helper.afterRequest
import com.wpf.app.quickrecyclerview.helper.autoRefresh
import com.wpf.app.quickrecyclerview.helper.autoRefreshOnlyAnim
import com.wpf.app.quickrecyclerview.helper.bindRefreshView
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickrecyclerview.listeners.request2List
import com.wpf.app.quickrecyclerview.listeners.requestData2List
import com.wpf.app.quickutil.LogUtil
import com.wpf.app.quickutil.base.asTo
import com.wpf.app.quickutil.widgets.getChild
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.memberFunctions

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
fun <T> View.request2View(apiCls: Class<T>, methodName: String, parameters: List<Any>?) {
    if (!apiCls.isInterface) return
    val parameterTypes: Array<Class<*>> = parameters?.map {
        if (it.javaClass == Integer::class.java) Int::class.java else it.javaClass
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

@BindingAdapter(
    value = ["apiClass", "methodName", "autoRefresh", "requestData", "otherArgument"],
    requireAll = false
)
fun <T : Any, R : RequestData> ViewGroup.request2List(
    apiCls: KClass<T>,
    methodName: String,
    autoRefresh: Boolean = true,
    requestData: R?,
    otherArgument: List<Pair<String, Any>>? = null
) {
    if (!apiCls.java.isInterface) return
    val refreshView: RefreshView = getChild<RefreshView> {
        it is RefreshView
    } ?: return
    val requestDataNew = requestData ?: RequestData()
    bindRefreshView(false)
    val method = apiCls.memberFunctions.find {
        it.name == methodName
    } ?: return
    val methodParametersName = method.parameters.map {
        it.name
    }.filter {
        !it.isNullOrEmpty()
    }
    val methodFields = methodParametersName.map { parameterName ->
        val findField = requestDataNew.javaClass.declaredFields.find {
            it.name == parameterName
        }
        findField?.isAccessible = true
        findField
    }
    val api: (T, R) -> RealCall<BaseResponseI<out QuickItemData, Any>, Any> =
        object : (T, R) -> RealCall<BaseResponseI<out QuickItemData, Any>, Any> {
            override fun invoke(
                p1: T,
                requestData: R
            ): RealCall<BaseResponseI<out QuickItemData, Any>, Any> {
                val methodValue = methodFields.map {
                    it?.get(requestData)
                }.toMutableList()
                methodValue.addAll(
                    if (otherArgument != null) {
                        methodParametersName.map { parameterName ->
                            otherArgument.find {
                                it.first == parameterName
                            }?.second
                        }
                    } else arrayListOf()
                )
                val methodTypeValue = methodValue.toTypedArray()
                return method.call(
                    p1, *methodTypeValue
                ) as RealCall<BaseResponseI<out QuickItemData, Any>, Any>
            }
        }
    val request2List = requestData2List<R, QuickItemData> { requestData, callback ->
        requestCls(apiCls.java) {
            api(this, requestData)
        }.success {
            if (it?.dataI is QuickRequestList) {
                callback.backData(it.dataI?.asTo<QuickRequestList>()?.returnList())
            } else if (it?.dataI is List<*>) {
                callback.backData(it.dataI?.asTo<List<QuickItemData>>())
            }
        }.after {
            afterRequest(requestData.isViewRefresh)
        }
    } as Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>
    request2List.asTo<Request2ListWithView<RequestData, out QuickItemData, out RefreshView>>()?.requestData =
        requestDataNew
    if (refreshView is QuickRefreshRecyclerView) {
        refreshView.requestData = requestDataNew
    }
    BindData2ViewHelper.bind(refreshView, request2List, Request2RefreshView)
    if (autoRefresh && requestData == null) {
        autoRefresh()
    }
    if (requestData != null) {
        request2List.manualRequest()
    }
}
