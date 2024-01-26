package com.wpf.app.quickutil.ability

import android.content.Context

interface QuickAbility<T, R>: QuickContextAbility<T, R> {
    fun run(self: T): R

    override fun run(context: Context, self: T): R {
        return run(self)
    }
}