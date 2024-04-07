package com.wpf.app.quickbind.utils

import com.wpf.app.base.bind.Bind
import com.wpf.app.base.bind.QuickBindWrap

interface FindViews: com.wpf.app.base.bind.Bind {

    fun findViews() {
        com.wpf.app.base.bind.QuickBindWrap.bind(this)
    }
}