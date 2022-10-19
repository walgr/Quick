package com.wpf.app.quickdialog.listeners

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wpf.app.quickdialog.listeners.DialogSize

/**
 * Created by 王朋飞 on 2022/6/21.
 */
interface SheetInit {
    fun initSheetState(): Int {
        return BottomSheetBehavior.STATE_HIDDEN
    }

    fun hideAble(): Boolean {
        return false
    }

    fun initPeekHeight(): Int {
        return DialogSize.NO_SET
    }

    /**
     * 是否可以手动滑动
     * @return
     */
    fun canScroll(): Boolean {
        return true
    }
}