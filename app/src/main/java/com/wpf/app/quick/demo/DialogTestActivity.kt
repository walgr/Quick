package com.wpf.app.quick.demo

import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.demo.dialog.TestDialog
import com.wpf.app.quickutil.bind.runOnContext

class DialogTestActivity : QuickActivity(
    layoutViewInContext = runOnContext {
        LinearLayout(it).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            addView(MaterialButton(it).apply {
                text = "弹窗"
                id = R.id.btnNext
            })
        }
    }
) {

    private var testDialog: TestDialog? = null
    override fun initView() {
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            testDialog = (testDialog ?: TestDialog(this))
            testDialog?.show()
        }
    }

}