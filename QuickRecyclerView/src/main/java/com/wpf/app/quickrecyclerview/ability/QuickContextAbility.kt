package com.wpf.app.quickrecyclerview.ability

import android.content.Context
import android.view.View
import com.wpf.app.quickrecyclerview.QuickAdapter

interface QuickContextAbility<T> {
    fun getPrimeKey(): String
    /**
     * 在QuickAdapter的onCreateViewHolder初始化Holder之前调用
     */
    fun onCreateHolder(context: Context, self: T) {

    }
    fun beforeCreateHolder(itemView: View, quickAdapter: QuickAdapter) {

    }
    fun afterCreateHolder(itemView: View, quickAdapter: QuickAdapter) {

    }
    fun beforeBindHolder(context: Context, self: T) {

    }
    fun afterBindHolder(context: Context, self: T) {

    }
}