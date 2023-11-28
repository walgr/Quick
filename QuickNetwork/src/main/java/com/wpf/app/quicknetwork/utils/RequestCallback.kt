package com.wpf.app.quicknetwork.utils

import com.wpf.app.quickutil.CallbackList

interface RequestCallback<Data>: CallbackList<Data> {
    override fun backData(data: List<Data>?) {
        backData(data, true)
    }

    fun backData(data: List<Data>?, hasMore: Boolean = true)
}