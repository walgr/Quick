package com.wpf.app.quickwidget.shadow.base

import android.content.Context
import androidx.lifecycle.MutableLiveData

typealias RealValueFun<T> = ((ShadowView) -> T?)
enum class ShadowDataEnum(
    val key: String,
    val callbackLive: () -> MutableLiveData<out Any>,
    val getRealValue: RealValueFun<out Any>? = null,
    val setRealValue: ((Context, ShadowView) -> Unit)? = null
) {
    TextLiveData("text",
        { MutableLiveData<CharSequence>() },
//        object : RealValueFun<CharSequence> {
//            override fun invoke(p1: ShadowView): CharSequence? {
//                return p1.getText()
//            }
//        },
//        object : ((Context, ShadowView) -> Unit) {
//            override fun invoke(context: Context, shadowView: ShadowView) {
//                shadowView.getLiveData<CharSequence>(TextLiveData.key)
//                    ?.observe(context as LifecycleOwner) {
//                        shadowView.setText(it, TextView.BufferType.NORMAL)
//                    }
//            }
//        }
    ),
//
//    TextSizeLiveData(
//        "textSize",
//        { MutableLiveData<Float>() },
//        object : (ShadowView) -> Float? {
//            override fun invoke(p1: ShadowView): Float {
//                return p1.getTextSize()
//            }
//        },
//        object : ((Context, ShadowView) -> Unit) {
//            override fun invoke(context: Context, shadowView: ShadowView) {
//                shadowView.getLiveData<Float>(TextSizeLiveData.key)
//                    ?.observe(context as LifecycleOwner) {
//                        shadowView.setTextSize(it)
//                    }
//            }
//        },
//    ),
//
//    TextColorLiveData(
//        "textColor",
//        { MutableLiveData<Int>() },
//        object : (ShadowView) -> Int? {
//            override fun invoke(p1: ShadowView): Int {
//                return p1.getTextColor()
//            }
//        },
//        object : ((Context, ShadowView) -> Unit) {
//            override fun invoke(context: Context, shadowView: ShadowView) {
//                shadowView.getLiveData<Int>(TextColorLiveData.key)
//                    ?.observe(context as LifecycleOwner) {
//                        shadowView.setTextColor(it)
//                    }
//            }
//        },
//    ),
//
//    TextColorStateListLiveData(
//        "textColorState", { MutableLiveData<ColorStateList>() },
//        object : (ShadowView) -> ColorStateList? {
//            override fun invoke(p1: ShadowView): ColorStateList? {
//                return p1.getTextColorStateList()
//            }
//        },
//        object : ((Context, ShadowView) -> Unit) {
//            override fun invoke(context: Context, shadowView: ShadowView) {
//                shadowView.getLiveData<ColorStateList>(TextColorStateListLiveData.key)
//                    ?.observe(context as LifecycleOwner) {
//                        shadowView.setTextColor(it)
//                    }
//            }
//        }
//    ),
//
//    CheckedLiveData(
//        "checked", { MutableLiveData<Boolean>() },
//        object : (ShadowView) -> Boolean? {
//            override fun invoke(p1: ShadowView): Boolean {
//                return p1.isChecked()
//            }
//        },
//        object : ((Context, ShadowView) -> Unit) {
//            override fun invoke(context: Context, shadowView: ShadowView) {
//                shadowView.getLiveData<Boolean>(CheckedLiveData.key)
//                    ?.observe(context as LifecycleOwner) {
//                        shadowView.setChecked(it)
//                    }
//            }
//        },
//    ),
//
//    ImageLiveData(
//        "src",
//        { MutableLiveData<Drawable>() },
//        object : (ShadowView) -> Drawable? {
//            override fun invoke(p1: ShadowView): Drawable? {
//                return p1.getDrawable()
//            }
//        },
//        object : ((Context, ShadowView) -> Unit) {
//            override fun invoke(context: Context, shadowView: ShadowView) {
//                shadowView.getLiveData<Drawable>(ImageLiveData.key)
//                    ?.observe(context as LifecycleOwner) {
//                        shadowView.setImageDrawable(it)
//                    }
//            }
//        }
//    ),
//
//    BackgroundLiveData("background",
//        { MutableLiveData<Drawable>() },
//        object : (ShadowView) -> Drawable? {
//            override fun invoke(p1: ShadowView): Drawable? {
//                return p1.getBackground()
//            }
//        },
//        object : ((Context, ShadowView) -> Unit) {
//            override fun invoke(context: Context, shadowView: ShadowView) {
//                shadowView.getLiveData<Drawable>(BackgroundLiveData.key)
//                    ?.observe(context as LifecycleOwner) {
//                        shadowView.setBackground(it)
//                    }
//            }
//
//        })
}