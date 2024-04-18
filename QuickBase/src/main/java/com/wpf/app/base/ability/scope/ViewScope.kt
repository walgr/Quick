package com.wpf.app.base.ability.scope

import android.view.View

interface ViewScope {
    val view: View
}

interface TextViewScope: ViewScope