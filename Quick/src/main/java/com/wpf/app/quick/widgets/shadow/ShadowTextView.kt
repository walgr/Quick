package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickutil.data.KVObject

class ShadowTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    override var key: String = ""
) : AppCompatTextView(context, attrs, defStyleAttr), ShadowView {

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
                funcKeyText to MutableLiveData<CharSequence?>()
            )
        }
        textLive?.observe(context as LifecycleOwner) {
            text = it
        }
    }

    override fun onAttachedToWindow() {
        super<AppCompatTextView>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
        if (textLive?.value == null) {
            textLive?.value = text
        }
    }

    override fun onDetachedFromWindow() {
        super<AppCompatTextView>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        if (!TextUtils.isEmpty(key) && textLive?.value != text) {
            textLive?.value = text
        }
    }
}