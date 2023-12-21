package com.wpf.app.quick.widgets.shadow.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quickutil.data.Quadruple

typealias ShadowData<T> = Quadruple<String, () -> MutableLiveData<out T>, ((Context, ShadowView) -> Unit)?, (ShadowView.() -> Unit)?>

object ShadowLiveFactory {
    val liveDataList by lazy {
        mutableMapOf(
            1 to visibilityLiveData,
            2 to backgroundLiveData,
            4 to textLiveData,
            8 to textSizeLiveData,
            10 to textColorLiveData,
            20 to textColorStateListLiveData,
            40 to isSelectLiveData,
            80 to checkedLiveData,
            100 to imageLiveData,
        )
    }
    fun addCustomData(liveData: Pair<Int, ShadowData<out Any>>) {
        liveDataList[liveData.first] = liveData.second
    }
    val liveDataAssembleList by lazy {
        val assembleMap = mutableMapOf<Int, List<ShadowData<out Any>>>()
        val assembleTemp = mutableMapOf<Int, ShadowData<out Any>>()
        val keys = liveDataList.keys
        keys.sorted().forEach {
            if (assembleTemp.isEmpty() || assembleTemp.keys.maxOf { key -> key.toString().length }.toString().length <= it.toString().length) {
                assembleTemp[it] = liveDataList[it] as ShadowData<out Any>
            } else {
                assembleTemp.clear()
                assembleTemp[it] = liveDataList[it] as ShadowData<out Any>
            }
            if (assembleTemp.size > 1) {
                assembleMap[assembleTemp.keys.sum()] = assembleTemp.values.flatMap { value ->
                    listOf(value)
                }
            }
        }
        assembleMap
    }

    private const val visibilityLiveDataKey = "visibility"
    val visibilityLiveData: ShadowData<Int> = Quadruple(
        visibilityLiveDataKey, { MutableLiveData<Int>() },
        object : ((Context, ShadowView) -> Unit) {
            override fun invoke(context: Context, shadowView: ShadowView) {
                shadowView.getLiveData<Int>(visibilityLiveDataKey)
                    ?.observe(context as LifecycleOwner) {
                        shadowView.setVisibility(it)
                    }
            }
        }, object : (ShadowView) -> Unit {
            override fun invoke(p1: ShadowView) {
                p1.getLiveData<Int>(visibilityLiveDataKey)?.nullSet(p1.getVisibility())
            }
        })

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
                    p1.getLiveData<TextSize>(textSizeLiveDataKey)
                        ?.nullSet(TextSize(TypedValue.COMPLEX_UNIT_PX, p1.getTextSize()))
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
                    p1.getLiveData<ColorStateList>(textColorStateListLiveDataKey)
                        ?.nullSet(p1.getTextColorStateList())
                }
            })

    private const val isSelectLiveDataKey = "isSelect"
    val isSelectLiveData: ShadowData<Boolean> =
        Quadruple(isSelectLiveDataKey, { MutableLiveData<Boolean>() },
            object : ((Context, ShadowView) -> Unit) {
                override fun invoke(context: Context, shadowView: ShadowView) {
                    shadowView.getLiveData<Boolean>(isSelectLiveDataKey)
                        ?.observe(context as LifecycleOwner) {
                            shadowView.setSelected(it)
                        }
                }

            }, object : (ShadowView) -> Unit {
                override fun invoke(p1: ShadowView) {
                    p1.getLiveData<Boolean>(isSelectLiveDataKey)?.nullSet(p1.isSelected())
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