package com.wpf.app.quickrecyclerview.ability

import android.content.Context
import android.view.View
import com.wpf.app.quickrecyclerview.QuickAdapter

interface QuickAbility<T> : QuickContextAbility<T> {

    fun beforeCreateHolder(quickAdapter: QuickAdapter) {

    }

    fun afterCreateHolder(quickAdapter: QuickAdapter) {

    }

    fun beforeBindHolder(self: T) {

    }

    fun afterBindHolder(self: T) {

    }

    override fun beforeCreateHolder(itemView: View, quickAdapter: QuickAdapter) {
        beforeCreateHolder(quickAdapter)
    }

    override fun afterCreateHolder(itemView: View, quickAdapter: QuickAdapter) {
        afterCreateHolder(quickAdapter)
    }

    override fun beforeBindHolder(context: Context, self: T) {
        beforeBindHolder(self)
    }

    override fun afterBindHolder(context: Context, self: T) {
        afterBindHolder(self)
    }

}