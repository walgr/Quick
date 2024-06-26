package com.wpf.app.quickrecyclerview.data

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 基类Item
 */
open class QuickItemData(
    var viewType: Int = 0,
) : Serializable {
    init {
        initViewType()
    }

    open fun initViewType() {
        if (viewType == 0) {
            viewType = javaClass.name.hashCode()
        }
    }

    open var spanSize: Int = 1
    open fun getSpanSize(mSpanCount: Int): Int {
        return spanSize
    }

    open var dealSpaceItemDecoration = true
    open fun dealSpaceItemDecoration(pos: Int): Boolean {
        return dealSpaceItemDecoration
    }

    open fun customAllRows(allCount: Int, spanCount: Int): Int? {
        return null
    }

    open fun customColumn(pos: Int, spanCount: Int): Int? {
        return null
    }

    open fun customRow(pos: Int, spanCount: Int): Int? {
        return null
    }

    open fun <T : QuickItemData> clone(): T {
        val byteArrayOutputStream = ByteArrayOutputStream()
        ObjectOutputStream(byteArrayOutputStream).apply {
            writeObject(this@QuickItemData)
            ObjectInputStream(ByteArrayInputStream(byteArrayOutputStream.toByteArray())).apply {
                return this.readObject() as T
            }
        }
    }
}

