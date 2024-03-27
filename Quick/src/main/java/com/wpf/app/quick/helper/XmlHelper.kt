package com.wpf.app.quick.helper

import android.app.Activity
import android.view.View
import android.widget.Checkable
import android.widget.CompoundButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickrecyclerview.constant.BRConstant.view
import com.wpf.app.quickwidget.item.CheckView
import com.wpf.app.quickutil.activity.quickStartActivity
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/5/18.
 *
 */

@BindingAdapter("clickGoto")
fun <T : Activity> gotoActivity(view: View, gotoClass: KClass<T>) {
    view.setOnClickListener {
        view.context.quickStartActivity(gotoClass.java)
    }
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