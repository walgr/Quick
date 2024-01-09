package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.wpf.app.quickdialog.QuickDialog
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.other.dp2px
import com.wpf.app.quickutil.other.wrapMarginLayoutParams

class Test3Dialog(
    context: Context
): QuickDialog(
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