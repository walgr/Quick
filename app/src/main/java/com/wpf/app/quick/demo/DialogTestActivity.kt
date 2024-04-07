package com.wpf.app.quick.demo

import android.view.Gravity
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.helper.myLayout
import com.wpf.app.quick.demo.dialog.Test2Dialog
import com.wpf.app.quick.demo.dialog.Test3Dialog
import com.wpf.app.quick.demo.dialog.TestDialog
import com.wpf.app.quickdialog.showInManager
import com.wpf.app.quickutil.run.runOnContext
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickwork.ability.title

class DialogTestActivity : QuickActivity(
    contentView<LinearLayout> {
        title("弹窗测试")
        myLayout(layoutViewInContext = runOnContext { context ->
            LinearLayout(context).apply {
                setPadding(16.dp(), 0, 16.dp(), 0)
                gravity = Gravity.CENTER
                addView(MaterialButton(context).apply {
                    text = "弹窗"
                    onceClick {
                        Test3Dialog(context).showInManager(showWithOther = true)
                        it.postDelayed({
                            Test2Dialog(context).showInManager(recoverInDismiss = false)
                        }, 1000)
                        it.postDelayed({
                            TestDialog(context).showInManager()
                        }, 2000)
                    }
                })
            }
        })
    }
)