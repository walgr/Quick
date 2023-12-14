package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickutil.data.KVObject

class ShadowToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    override var key: String = ""
) : AppCompatToggleButton(context, attrs), ShadowView {

    private val funcKeyChecked = "checked"
    private val checkedLive by lazy {
        KVObject.get<Map<String, MutableLiveData<Boolean>>>(key)?.get(funcKeyChecked)
    }
    private val funcKeyText = "text"
    private val textLive by lazy {
        KVObject.get<Map<String, MutableLiveData<CharSequence?>>>(key)?.get(funcKeyText)
    }

    init {
        if (TextUtils.isEmpty(key)) {
            key = ShadowViewAttr(context, attrs).key ?: ""
        }
        KVObject.putIfNull(key) {
            mapOf(
                funcKeyChecked to MutableLiveData<Boolean>(),
                funcKeyText to MutableLiveData<CharSequence?>()
            )
        }
        checkedLive?.observe(context as LifecycleOwner) {
            isChecked = it
        }
        textLive?.observe(context as LifecycleOwner) {
            text = it
        }
    }

    override fun onAttachedToWindow() {
        super<AppCompatToggleButton>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
        if (checkedLive?.value == null) {
            checkedLive?.value = isChecked
        }
        if (textLive?.value == null) {
            textLive?.value = text
        }
    }

    override fun onDetachedFromWindow() {
        super<AppCompatToggleButton>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        if (!TextUtils.isEmpty(key) && checkedLive?.value != checked) {
            checkedLive?.value = checked
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        if (!TextUtils.isEmpty(key) && textLive?.value != text) {
            textLive?.value = text
        }
    }
}