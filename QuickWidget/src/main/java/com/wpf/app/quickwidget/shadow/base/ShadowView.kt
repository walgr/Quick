package com.wpf.app.quickwidget.shadow.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickwidget.shadow.base.ShadowLiveFactory.liveDataAssembleList
import com.wpf.app.quickwidget.shadow.base.ShadowLiveFactory.liveDataList
import com.wpf.app.quickutil.data.KV

interface ShadowView {
    val attrs: AttributeSet?
    var key: String
    var bindTypes: List<ShadowData<out Any>>?

    private fun getBuildType(buildTypeStr: String?): List<String>? {
        if (buildTypeStr == null) return null
        val result = mutableListOf<String>()
        buildTypeStr.toCharArray().reversed().filterIndexed { index, c ->
            if (index != 0) {
                val fillInStr = (10 * index).toString()
                result.add(c.toString() + fillInStr.substring(fillInStr.length - 1))
            } else {
                result.add(c.toString())
            }
        }
        return result.filter {
            it != "0"
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun initShadow(context: Context, liveKey: List<ShadowData<Any>>) {
        if (TextUtils.isEmpty(key)) {
            val attr = ShadowViewAttr(context, attrs)
            key = attr.key ?: ""
            val bindTypeStr = getBuildType(attr.bindTypes?.replace("0x", ""))
            bindTypes = if (bindTypeStr == null) liveKey else liveDataList.filter {
                bindTypeStr.contains(it.key.toString())
            }.values.toMutableList().plus(
                bindTypeStr.flatMap {
                    liveDataAssembleList[it.hexToInt(HexFormat.Default)] ?: mutableListOf()
                }.toMutableList()
            )
        }
        if (bindTypes == null) return
        var keyLiveMap = KV.get<MutableMap<String, MutableLiveData<out Any>>>(key)
        if (keyLiveMap == null) {
            val liveKeyFilterMap = bindTypes!!.associate {
                Pair(it.first, it.second.invoke())
            }.toMutableMap()
            KV.put(key, liveKeyFilterMap)
            keyLiveMap = liveKeyFilterMap
        } else {
            val liveKeyFilterMap = bindTypes!!.filter {
                !keyLiveMap.keys.contains(it.first)
            }.associate {
                Pair(it.first, it.second.invoke())
            }.toMutableMap()
            keyLiveMap.putAll(liveKeyFilterMap)
        }
        bindTypes!!.forEach {
            it.third?.invoke(context, this)
        }
    }

    fun setVisibility(visibility: Int) {
        checkKey {
            getLiveData<Int>(ShadowLiveFactory.visibilityLiveData.first)?.noEqualSet(visibility)
        }
    }

    fun getVisibility(): Int {
        return View.VISIBLE
    }

    fun getText(): CharSequence? {
        return null!!
    }

    fun setText(text: CharSequence?, type: TextView.BufferType?) {
        checkKey {
            getLiveData<CharSequence>(ShadowLiveFactory.textLiveData.first)?.noEqualSet(text)
        }
    }

    fun setTextColor(@ColorInt color: Int) {
        checkKey {
            getLiveData<Int>(ShadowLiveFactory.textColorLiveData.first)?.noEqualSet(color)
        }
    }

    fun setTextColor(colors: ColorStateList?) {
        checkKey {
            getLiveData<ColorStateList>(ShadowLiveFactory.textColorStateListLiveData.first)?.noEqualSet(colors)
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
            getLiveData<TextSize>(ShadowLiveFactory.textSizeLiveData.first).noEqualSet(TextSize(unit, size)) { o1, o2 ->
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
            getLiveData<Boolean>(ShadowLiveFactory.checkedLiveData.first)?.noEqualSet(checked)
        }
    }

    fun isSelected(): Boolean {
        return false
    }

    fun setSelected(selected: Boolean) {
        checkKey {
            getLiveData<Boolean>(ShadowLiveFactory.isSelectLiveData.first)?.noEqualSet(selected)
        }
    }

    fun setImageDrawable(drawable: Drawable?) {
        checkKey {
            getLiveData<Drawable>(ShadowLiveFactory.imageLiveData.first)?.noEqualSet(drawable)
        }
    }

    fun setBackground(background: Drawable?) {
        checkKey {
            getLiveData<Drawable>(ShadowLiveFactory.backgroundLiveData.first)?.noEqualSet(background)
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
        liveDataList.forEach {
            it.value.four?.invoke(this)
        }
    }
    fun onDetachedFromWindow() {
        ShadowViewManager.onDetachedFromWindow(this)
    }

    fun <T: Any> getLiveData(mapKey: String?): MutableLiveData<T>? {
        return KV.get<Map<String, MutableLiveData<T>>>(key)?.get(mapKey)
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