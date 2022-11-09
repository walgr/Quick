package com.wpf.app.quickbind.plugins

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.BindSp2View
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindSp2ViewPlugin : BindBasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ) {
        val findViewA: BindSp2View =
            field.getAnnotation(BindSp2View::class.java) ?: return
        field.isAccessible = true
        val findView = field[getRealObj(obj, viewModel)] as View
        if (findView is TextView) {
            setTextViewValue(
                findView,
                QuickBind.getBindSpFileName(),
                findViewA.bindSp,
                findViewA.setSp,
                findViewA.getSp,
                findViewA.defaultValue
            )
        }
        field[getRealObj(obj, viewModel)] = findView
        return

    }

    private fun setTextViewValue(
        textView: TextView,
        spFileName: String,
        bindSpKey: String,
        setSpKey: String,
        getSpKey: String,
        defaultValue: String
    ) {
        var key = ""
        if (bindSpKey.isNotEmpty()) {
            key = bindSpKey
        } else if (setSpKey.isNotEmpty()) {
            key = setSpKey
        } else if (getSpKey.isNotEmpty()) {
            key = getSpKey
        }
        if (key.isEmpty()) return
        val value = textView.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
            .getString(key, defaultValue)
        if (bindSpKey.isNotEmpty() || setSpKey.isNotEmpty()) {
            val finalKey = key
            textView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() != value) {
                        textView.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                            .edit()
                            .putString(finalKey, s.toString()).apply()
                    }
                }

                override fun afterTextChanged(s: Editable) {}
            })
            if (bindSpKey.isNotEmpty() || getSpKey.isNotEmpty()) {
                textView.text = value
            }
        }
    }
}