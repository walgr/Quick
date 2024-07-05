package com.wpf.app.quick.demo

import android.view.Gravity
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.wpf.app.quickutil.ability.ex.contentView
import com.wpf.app.quickutil.ability.helper.gravityInParent
import com.wpf.app.quickutil.ability.helper.rect
import com.wpf.app.quickutil.ability.helper.viewGroupCreate
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.helper.bottomSheetDialog
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.onClick
import com.wpf.app.quickutil.helper.paddingHorizontal
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.helper.sp
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.helper.weight
import com.wpf.app.quickwidget.wheel.WheelItemData
import com.wpf.app.quickwidget.wheel.WheelView
import com.wpf.app.quickwork.ability.helper.text
import com.wpf.app.quickwork.ability.helper.title

@GetClass
class BottomSheetDialogTestActivity : QuickActivity(
    contentView<LinearLayout> {
        title("BottomSheet测试")
        viewGroupCreate<LinearLayout>(layoutParams = matchMarginLayoutParams()) {
            addView(MaterialButton(context).apply {
                text = "弹窗"
            }.onClick {
                bottomSheetDialog(
                    layoutViewCreate = {
                        viewGroupCreate<LinearLayout>(matchMarginLayoutParams()) {
                            view.orientation = LinearLayout.VERTICAL
                            rect(
                                color = R.color.white.toColor(),
                                topLeftRadius = 16f.dp,
                                topRightRadius = 16f.dp
                            )
                            viewGroupCreate<LinearLayout>(
                                layoutParams = matchWrapMarginLayoutParams().reset(
                                    height = 60.dp
                                )
                            ) {
                                viewApply {
                                    gravity = Gravity.CENTER_VERTICAL
                                    paddingHorizontal(16.dp)
                                }
                                text(
                                    textSize = 16f.sp,
                                    text = "取消",
                                    textColor = R.color.colorPrimary.toColor()
                                )
                                text(
                                    textSize = 18f.sp,
                                    text = "",
                                    textColor = R.color.colorPrimary.toColor()
                                ).weight(1f)
                                text(
                                    textSize = 16f.sp,
                                    text = "取消",
                                    textColor = R.color.colorPrimary.toColor()
                                )
                            }
                            val wheelView = WheelView(context)
                            wheelView.layoutParams = matchMarginLayoutParams()
                            val testData: MutableList<WheelItemData> = mutableListOf()
                            repeat(50) {
                                testData.add(WheelItemData(it.toString(), it.toString()))
                            }
                            wheelView.setData(testData)
                            addView(wheelView)
                        }
                    },
                    skipCollapsed = true,
                    heightAdaptive = true
                ).show(self)
            })
        }.gravityInParent(Gravity.CENTER)
    }
)