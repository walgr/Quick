package com.wpf.app.quickbind.quickview.helper

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.wpf.app.quickbind.quickview.QuickBindView
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.quickStartActivity

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
                context.quickStartActivity(activityCls, data)
            }
        }
    }
)