package com.wpf.app.quickbind.utils

import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.bind.QuickBindWrap

interface FindViews: Bind {

    fun findViews() {
        QuickBindWrap.bind(this)
    }
}