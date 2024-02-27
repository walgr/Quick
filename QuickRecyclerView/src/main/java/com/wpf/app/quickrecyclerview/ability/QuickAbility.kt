package com.wpf.app.quickrecyclerview.ability

import android.content.Context
import android.view.View

interface QuickAbility<T> : QuickContextAbility<T> {

    fun beforeCreateHolder(self: T) {

    }

    fun afterCreateHolder(self: T) {

    }

    fun beforeBindHolder(self: T) {

    }

    fun afterBindHolder(self: T) {

    }

    override fun beforeOnCreateHolder(itemView: View, selfOnlyFirst: T) {
        beforeCreateHolder(selfOnlyFirst)
    }

    override fun afterOnCreateHolder(itemView: View, selfOnlyFirst: T) {
        afterCreateHolder(selfOnlyFirst)
    }

    override fun beforeOnBindHolder(context: Context, self: T) {
        beforeBindHolder(self)
    }

    override fun afterOnBindHolder(context: Context, self: T) {
        afterBindHolder(self)
    }

}