package com.wpf.app.quickutil.ability

import android.content.Context

interface QuickContextAbility<T, R> {
    fun run(context: Context, self: T): R
}