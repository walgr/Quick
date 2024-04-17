package com.wpf.app.quick.demo

import android.view.Gravity
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.helper.background
import com.wpf.app.quick.ability.helper.bottomSheetDialog
import com.wpf.app.quick.ability.helper.gravity
import com.wpf.app.quick.ability.helper.myLayout
import com.wpf.app.quick.ability.helper.rect
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.onClick
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.run.runOnContext
import com.wpf.app.quickwork.ability.helper.text
import com.wpf.app.quickwork.ability.helper.title

@GetClass
class BottomSheetDialogTestActivity : QuickActivity(
    contentView<LinearLayout> { quickView ->
        title("BottomSheet测试")
        myLayout<LinearLayout> {
            addView(MaterialButton(context).apply {
                text = "弹窗"
            }.onClick {
                bottomSheetDialog(
                    layoutViewInContext = runOnContext {
                        myLayout<LinearLayout>(
                            layoutParams = matchWrapLayoutParams().reset(height = 100.dp()),
                        ) {
                            text(text = "弹窗")
                        }.gravity(Gravity.CENTER).background {
                            rect(
                                topLeftRadius = 16.dpF(),
                                topRightRadius = 16.dpF(),
                                color = R.color.white.toColor()
                            )
                        }
                    },
                    skipCollapsed = true,
                    heightAdaptive = true
                ).show(quickView)
            })
        }.gravity(Gravity.CENTER)
    }
)