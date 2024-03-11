package com.wpf.app.quick.demo

import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.demo.dialog.Test2Dialog
import com.wpf.app.quick.demo.dialog.Test3Dialog
import com.wpf.app.quick.demo.dialog.TestDialog
import com.wpf.app.quickdialog.showInManager
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickwork.activity.contentWithTitle

class DialogTestActivity : QuickAbilityActivity(
    contentWithTitle(layoutViewInContext = runOnContext {
        LinearLayout(it).apply {
            setPadding(16.dp(), 0, 16.dp(), 0)
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            addView(MaterialButton(it).apply {
                text = "弹窗"
                id = R.id.btnNext
            })
        }
    }, titleName = "弹窗测试") {
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            Test3Dialog(context).showInManager(showWithOther = true)
            it.postDelayed({
                Test2Dialog(context).showInManager(recoverInDismiss = false)
            }, 1000)
            it.postDelayed({
                TestDialog(context).showInManager()
            }, 2000)
        }
    }
)