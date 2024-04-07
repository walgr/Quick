package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.wpf.app.quickdialog.QuickBaseDialog
import com.wpf.app.quickutil.run.runOnContext

class Test3Dialog(
    context: Context
): QuickBaseDialog(
    context,
    layoutViewInContext = runOnContext {
        TextView(context).apply {
            text = "Test3Dialog"
        }
    }
) {
    override fun initView(view: View?) {
        
    }
}