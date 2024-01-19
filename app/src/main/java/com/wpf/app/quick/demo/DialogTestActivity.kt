package com.wpf.app.quick.demo

import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.demo.dialog.Test2Dialog
import com.wpf.app.quick.demo.dialog.Test3Dialog
import com.wpf.app.quick.demo.dialog.TestDialog
import com.wpf.app.quickdialog.showInManager
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.view.dp2px

class DialogTestActivity : QuickActivity(
    layoutViewInContext = runOnContext {
        LinearLayout(it).apply {
            setPadding(16.dp2px(it), 0, 16.dp2px(it), 0)
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            addView(MaterialButton(it).apply {
                text = "弹窗"
                id = R.id.btnNext
            })
        }
    }
) {

    override fun initView(view: View?) {
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            Test3Dialog(this).showInManager(showWithOther = true)
            it.postDelayed({
                Test2Dialog(this).showInManager(recoverInDismiss = false)
            }, 1000)
            it.postDelayed({
                TestDialog(this).showInManager()
            }, 2000)
        }
    }

}