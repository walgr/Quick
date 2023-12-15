package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickutil.data.KVObject

interface ShadowView {
    val attrs: AttributeSet?
    var key: String

    fun initShadow(context: Context, liveKey: List<ShadowData<Any>>) {
        if (TextUtils.isEmpty(key)) {
            key = ShadowViewAttr(context, attrs).key ?: ""
        }
        KVObject.putIfNull(key) {
            liveKey.associate {
                Pair(it.first, it.second.invoke())
            }
        }
        liveKey.forEach {
            it.third?.invoke(context, this)
        }
    }

    fun getText(): CharSequence? {
        return null!!
    }

    fun setText(text: CharSequence?, type: TextView.BufferType?) {
        checkKey {
            getLiveData<CharSequence>(ShadowLive.textLiveData.first)?.noEqualSet(text)
        }
    }

    fun setTextColor(@ColorInt color: Int) {
        checkKey {
            getLiveData<Int>(ShadowLive.textColorLiveData.first)?.noEqualSet(color)
        }
    }

    fun setTextColor(colors: ColorStateList?) {
        checkKey {
            getLiveData<ColorStateList>(ShadowLive.textColorStateListLiveData.first)?.noEqualSet(colors)
        }
    }

    fun getTextColor(): Int {
        return 0
    }

    fun getTextColorStateList(): ColorStateList? {
        return null
    }

    fun setTextSize(unit: Int, size: Float) {
        checkKey {
            getLiveData<TextSize>(ShadowLive.textSizeLiveData.first).noEqualSet(TextSize(unit, size)) { o1, o2 ->
                o1?.unit == o2?.unit && o1?.size == o2?.size
            }
        }
    }

    fun getTextSize(): Float {
        return 0f
    }

    fun isChecked(): Boolean {
        return false
    }

    fun setChecked(checked: Boolean) {
        checkKey {
            getLiveData<Boolean>(ShadowLive.checkedLiveData.first)?.noEqualSet(checked)
        }
    }

    fun setImageDrawable(drawable: Drawable?) {
        checkKey {
            getLiveData<Drawable>(ShadowLive.imageLiveData.first)?.noEqualSet(drawable)
        }
    }

    fun setBackground(background: Drawable?) {
        checkKey {
            getLiveData<Drawable>(ShadowLive.backgroundLiveData.first)?.noEqualSet(background)
        }
    }

    fun getDrawable(): Drawable? {
        return null
    }

    fun getBackground(): Drawable? {
        return null
    }

    fun onAttachedToWindow() {
        ShadowViewManager.onAttachedToWindow(this)
        ShadowLive.liveDataList.forEach {
            it.four?.invoke(this)
        }
    }
    fun onDetachedFromWindow() {
        ShadowViewManager.onDetachedFromWindow(this)
    }

    fun <T: Any> getLiveData(mapKey: String?): MutableLiveData<T>? {
        return KVObject.get<Map<String, MutableLiveData<T>>>(key)?.get(mapKey)
    }

    fun checkKey(result: () -> Unit) {
        if (!TextUtils.isEmpty(key)) {
            result.invoke()
        }
    }
    fun <T: Any> MutableLiveData<T>?.noEqualSet(other: T?, judge: ((o1: T?, o2: T?) -> Boolean)? = null) {
        if (this?.value != other || judge?.invoke(this?.value, other) == false) {
            this?.value = other
        }
    }
}