package com.wpf.app.quickutil.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Int.getView(context: Context, mParent: ViewGroup? = null): View = View.inflate(context, this, mParent)
fun Int.getInflaterView(context: Context, mParent: ViewGroup? = null): View = LayoutInflater.from(context).inflate(this, mParent, false)