package com.wpf.app.quick.widgets.quickview.helper

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.wpf.app.quick.widgets.quickview.QuickBindView
import com.wpf.app.quickbind.interfaces.runOnContext
import com.wpf.app.quickutil.startActivity

open class GotoThis @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    buttonText: String,
    activityCls: Class<out Activity>,
    data: Map<String, Any?>? = null
) : QuickBindView(
    mContext, attributeSet, layoutView = runOnContext { context ->
        MaterialButton(context, attributeSet).apply {
            text = buttonText
            setOnClickListener {
                context.startActivity(activityCls, data)
            }
        }
    }
)