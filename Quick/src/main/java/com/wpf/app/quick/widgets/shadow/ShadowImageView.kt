package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickutil.data.KVObject

class ShadowImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    override var key: String = ""
) : AppCompatImageView(context, attrs), ShadowView {

    private val funcKeyImage = "image"
    private val imageLive by lazy {
        KVObject.get<Map<String, MutableLiveData<Drawable?>>>(key)?.get(funcKeyImage)
    }
    private val funcKeyBackground = "background"
    private val backgroundLive by lazy {
        KVObject.get<Map<String, MutableLiveData<Drawable?>>>(key)?.get(funcKeyBackground)
    }

    init {
        if (TextUtils.isEmpty(key)) {
            key = ShadowViewAttr(context, attrs).key ?: ""
        }
        KVObject.putIfNull(key) {
            mapOf(
                funcKeyImage to MutableLiveData<Drawable?>(),
                funcKeyBackground to MutableLiveData<Drawable?>()
            )
        }
        imageLive?.observe(context as LifecycleOwner) {
            setImageDrawable(it)
        }
        backgroundLive?.observe(context as LifecycleOwner) {
            setBackgroundDrawable(it)
        }
    }

    override fun onAttachedToWindow() {
        super<AppCompatImageView>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
        if (imageLive?.value == null) {
            imageLive?.value = drawable
        }
        if (backgroundLive?.value == null) {
            backgroundLive?.value = background
        }
    }

    override fun onDetachedFromWindow() {
        super<AppCompatImageView>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }
}