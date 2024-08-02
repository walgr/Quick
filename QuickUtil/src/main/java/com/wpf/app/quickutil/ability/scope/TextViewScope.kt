package com.wpf.app.quickutil.ability.scope

import android.widget.TextView

interface TextViewScope: ViewScope<TextView>

fun <T: TextView> createTextViewScope(view: T) = object : TextViewScope {
    override val view: T = view
}