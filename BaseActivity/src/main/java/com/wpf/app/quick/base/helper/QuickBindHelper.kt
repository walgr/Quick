package com.wpf.app.quick.base.helper

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.wpf.app.quick.base.widgets.recyclerview.QuickViewHolder
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoGet(val key: String = "")

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class FindView(
    @IdRes val id: Int,
    val bindSp: String = "",        //View的text和Sp双向绑定
    val setSp: String = "",         //View的text和Sp单向改变 view改变->Sp改变
    val getSp: String = "",         //View的text和Sp单向获取 view显示时获取Sp
    val default: String = ""
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class GroupView(
    @IdRes val value: IntArray
)


/**
 * Created by 王朋飞 on 2022/6/8.
 * 自动获取Activity、Fragment里Intent里的值到属性上
 * 支持 char、byte、int、float、long、short、double、String、array、list、map、Serializable、Parcelable
 * 暂不支持 Parcelable[]
 */
object QuickBindHelper {

    var bindSpFileName = "QuickViewSpBindFile"

    //支持Activity和ViewModel
    fun bind(activity: Activity, viewModel: ViewModel? = null) {
        setDataByIntent(activity.intent.extras, viewModel ?: activity)
        findView(activity, viewModel)
    }

    //支持Fragment和ViewModel
    fun bind(fragment: Fragment, viewModel: ViewModel? = null) {
        setDataByIntent(fragment.arguments, viewModel ?: fragment)
        findView(fragment, viewModel)
    }

    //支持View
    fun bind(viewHolder: QuickViewHolder<*>) {
        findView(viewHolder, null)
    }

    private fun setDataByIntent(bundle: Bundle?, obj: Any) {
        if (bundle == null) return
        try {
            val fields = obj::class.java.declaredFields
            //本类传输数据
            fields.forEach { field ->
                setFieldData(bundle, obj, field)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun findView(obj: Any, viewModel: ViewModel?) {
        try {
            val fields = obj::class.java.declaredFields
            //本类传输数据
            fields.forEach { field ->
                field.isAccessible = true
                if (field.get(obj) == null) {
                    setFieldView(obj, null, field)
                }
            }
            viewModel?.let {
                val viewModelFields = viewModel::class.java.declaredFields
                //本类传输数据
                viewModelFields.forEach { field ->
                    setFieldView(obj, viewModel, field)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setFieldData(bundle: Bundle, obj: Any, field: Field) {
        field.getAnnotation(AutoGet::class.java)?.let {
            field.isAccessible = true
            val key = it.key.ifEmpty { field.name }
            if (field.type.interfaces.contains(Parcelable::class.java)) {
                try {
                    //Parcelable
                    bundle.getParcelable<Parcelable>(key)?.let { value ->
                        field.set(obj, value)
                        return
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            try {
                //Serializable
                bundle.getSerializable(key)?.let { value ->
                    if (value is Array<*>) {
                        if (value.isNotEmpty() && value[0] is Parcelable) {
                            //TODO 暂不支持Parcelable[]
                        } else {
                            field.set(obj, value)
                        }
                    } else {
                        field.set(obj, value)
                    }
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                if (getFieldType(field) == 2) {
                    //ArrayList
                    bundle.getParcelableArrayList<Parcelable>(key)?.let { value ->
                        field.set(obj, value)
                        return
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 获取属性类型
     * @return 0 未知 1 array 2 list
     */
    private fun getFieldType(field: Field): Int {
        val type = field.genericType
        if (type is Class<*> && type.isArray) {
            return 1
        } else if (type is ParameterizedType) {
            return 2
        }
        return 0;
    }

    private fun setFieldView(obj: Any, viewModel: ViewModel?, field: Field) {
        field.getAnnotation(FindView::class.java)?.let {
            field.isAccessible = true
            var findView: View? = null
            if (obj is Activity) {
                findView = obj.findViewById(it.id)
            }
            if (obj is Fragment) {
                findView = obj.view?.findViewById(it.id)
            }
            if (obj is QuickViewHolder<*>) {
                findView = obj.itemView.findViewById(it.id)
            }
            if (findView is TextView) {
                setTextViewValue(
                    findView,
                    bindSpFileName,
                    it.bindSp,
                    it.setSp,
                    it.getSp,
                    it.default
                )
            }
            viewModel?.let {
                field.set(viewModel, findView)
            } ?: let {
                field.set(obj, findView)
            }
        }
    }

    private fun setTextViewValue(
        textView: TextView,
        fileName: String,
        bindKey: String,
        setKey: String,
        getKey: String,
        defaultValue: String
    ) {
        val key: String = if (bindKey.isNotEmpty()) {
            bindKey
        } else if (setKey.isNotEmpty()) {
            setKey
        } else {
            getKey
        }
        if (key.isEmpty()) return
        val spValue = textView.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            .getString(key, defaultValue)
        if (bindKey.isNotEmpty() || setKey.isNotEmpty()) {
            textView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        if (spValue != s.toString()) {
                            textView.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                                .edit()
                                .putString(key, it.toString()).apply()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
        if (bindKey.isNotEmpty() || getKey.isNotEmpty()) {
            textView.text = spValue
        }
    }
}