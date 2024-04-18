package com.wpf.app.quick.ability.scope

import android.view.View

interface ViewScope {
    val view: View
}

interface TextViewScope: ViewScope