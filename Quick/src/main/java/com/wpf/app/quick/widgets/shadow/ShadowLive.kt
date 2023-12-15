package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickutil.data.Quadruple

typealias ShadowData<T> = Quadruple<String, () -> MutableLiveData<out T>, ((Context, ShadowView) -> Unit)?, (ShadowView.() -> Unit)?>

object ShadowLive {
    val liveDataList by lazy {
        arrayOf(
            textLiveData,
            textColorLiveData,
            textSizeLiveData,
            textColorStateListLiveData,
            checkedLiveData,
            imageLiveData,
            backgroundLiveData
        )
    }

    private const val textLiveDataKey = "text"
    val textLiveData: ShadowData<CharSequence> = Quadruple(
        textLiveDataKey, { MutableLiveData<CharSequence>() },
        object : ((Context, ShadowView) -> Unit) {
            override fun invoke(context: Context, shadowView: ShadowView) {
                shadowView.getLiveData<CharSequence>(textLiveDataKey)
                    ?.observe(context as LifecycleOwner) {
                        shadowView.setText(it, TextView.BufferType.NORMAL)
                    }
            }
        }, object : (ShadowView) -> Unit {
            override fun invoke(p1: ShadowView) {
                p1.getLiveData<CharSequence>(textLiveDataKey)?.nullSet(p1.getText())
            }
        })

    private const val textSizeLiveDataKey = "textSize"
    val textSizeLiveData: ShadowData<TextSize> =
        Quadruple(textSizeLiveDataKey, { MutableLiveData<TextSize>() },
            object : ((Context, ShadowView) -> Unit) {
                override fun invoke(context: Context, shadowView: ShadowView) {
                    shadowView.getLiveData<TextSize>(textSizeLiveDataKey)
                        ?.observe(context as LifecycleOwner) {
                            shadowView.setTextSize(it.unit, it.size)
                        }
                }
            }, object : (ShadowView) -> Unit {
                override fun invoke(p1: ShadowView) {
                    p1.getLiveData<TextSize>(textSizeLiveDataKey)?.nullSet(TextSize(TypedValue.COMPLEX_UNIT_PX, p1.getTextSize()))
                }
            })

    private const val textColorLiveDataKey = "textColor"
    val textColorLiveData: ShadowData<Int> =
        Quadruple(textColorLiveDataKey, { MutableLiveData<Int>() },
            object : ((Context, ShadowView) -> Unit) {
                override fun invoke(context: Context, shadowView: ShadowView) {
                    shadowView.getLiveData<Int>(textColorLiveDataKey)
                        ?.observe(context as LifecycleOwner) {
                            shadowView.setTextColor(it)
                        }
                }
            }, object : (ShadowView) -> Unit {
                override fun invoke(p1: ShadowView) {
                    p1.getLiveData<Int>(textColorLiveDataKey)?.nullSet(p1.getTextColor())
                }
            })

    private const val textColorStateListLiveDataKey = "textColorStateList"
    val textColorStateListLiveData: ShadowData<ColorStateList> =
        Quadruple(textColorStateListLiveDataKey, { MutableLiveData<ColorStateList>() },
            object : ((Context, ShadowView) -> Unit) {
                override fun invoke(context: Context, shadowView: ShadowView) {
                    shadowView.getLiveData<ColorStateList>(textColorStateListLiveDataKey)
                        ?.observe(context as LifecycleOwner) {
                            shadowView.setTextColor(it)
                        }
                }

            }, object : (ShadowView) -> Unit {
                override fun invoke(p1: ShadowView) {
                    p1.getLiveData<ColorStateList>(textColorStateListLiveDataKey)?.nullSet(p1.getTextColorStateList())
                }
            })

    private const val checkedLiveDataKey = "checked"
    val checkedLiveData: ShadowData<Boolean> =
        Quadruple(checkedLiveDataKey, { MutableLiveData<Boolean>() },
            object : ((Context, ShadowView) -> Unit) {
                override fun invoke(context: Context, shadowView: ShadowView) {
                    shadowView.getLiveData<Boolean>(checkedLiveDataKey)
                        ?.observe(context as LifecycleOwner) {
                            shadowView.setChecked(it)
                        }
                }

            }, object : (ShadowView) -> Unit {
                override fun invoke(p1: ShadowView) {
                    p1.getLiveData<Boolean>(checkedLiveDataKey)?.nullSet(p1.isChecked())
                }
            })

    private const val imageLiveDataKey = "image"
    val imageLiveData: ShadowData<Drawable> =
        Quadruple(
            imageLiveDataKey,
            { MutableLiveData<Drawable>() },
            object : ((Context, ShadowView) -> Unit) {
                override fun invoke(context: Context, shadowView: ShadowView) {
                    shadowView.getLiveData<Drawable>(imageLiveDataKey)
                        ?.observe(context as LifecycleOwner) {
                            shadowView.setImageDrawable(it)
                        }
                }

            }, object : (ShadowView) -> Unit {
                override fun invoke(p1: ShadowView) {
                    p1.getLiveData<Drawable>(imageLiveDataKey)?.nullSet(p1.getDrawable())
                }
            })

    private const val backgroundLiveDataKey = "background"
    val backgroundLiveData: ShadowData<Drawable> =
        Quadruple(backgroundLiveDataKey, { MutableLiveData<Drawable>() },
            object : ((Context, ShadowView) -> Unit) {
                override fun invoke(context: Context, shadowView: ShadowView) {
                    shadowView.getLiveData<Drawable>(backgroundLiveDataKey)
                        ?.observe(context as LifecycleOwner) {
                            shadowView.setBackground(it)
                        }
                }

            }, object : (ShadowView) -> Unit {
                override fun invoke(p1: ShadowView) {
                    p1.getLiveData<Drawable>(backgroundLiveDataKey)?.nullSet(p1.getBackground())
                }
            })

    fun <T: Any> MutableLiveData<T>.nullSet(other: T?) {
        if (this.value == null) {
            this.value = other
        }
    }
}