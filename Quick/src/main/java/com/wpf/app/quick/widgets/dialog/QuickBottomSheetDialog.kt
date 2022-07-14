package com.wpf.app.quick.widgets.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wpf.app.quick.helper.DialogSheetHelper
import com.wpf.app.quick.helper.DialogSizeHelper

/**
 * Created by 王朋飞 on 2022/6/21.
 */
abstract class QuickBottomSheetDialog : BottomSheetDialog,
    DialogSize, DialogLifecycle, SheetInit {

    @LayoutRes
    var layoutId: Int = 0
    var layoutView: View? = null

    constructor(
        mContext: Context,
        themeId: Int = 0,
        layoutId: Int = 0,
        layoutView: View? = null
    ) : super(mContext, themeId) {
        this.layoutId = layoutId
        this.layoutView = layoutView
    }

    constructor(
        mContext: Context,
        cancelable: Boolean = false,
        cancelListener: DialogInterface.OnCancelListener? = null,
        layoutId: Int = 0,
        layoutView: View? = null
    ) : super(mContext, cancelable, cancelListener) {
        this.layoutId = layoutId
        this.layoutView = layoutView
    }

    protected var mView: View? = null
    override fun getView(): View? {
        return mView
    }

    protected var mBehavior: BottomSheetBehavior<View>? = null

    override fun onStart() {
        super.onStart()
        mBehavior = DialogSheetHelper.dealSheet(this)
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealSize()
        mView = if (layoutView != null) {
            layoutView
        } else {
            View.inflate(context, layoutId, null)
        }
        setContentView(mView!!)
        val window = window
        if (window != null) {
            if (initDialogAnim() != DialogSize.NO_SET) {
                window.setWindowAnimations(initDialogAnim())
            }
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        initView(mView)
    }

    abstract fun initView(view: View?)

    protected var mScreenWidth = 0
    protected var mScreenHeight = 0

    protected fun dealSize() {
        val size = getScreenSize()
        mScreenWidth = size.x
        mScreenHeight = size.y
    }

    protected var mNewWidth = DialogSize.NO_SET
    protected var mNewHeight = DialogSize.NO_SET

    override fun getNewHeight(): Int {
        return mNewHeight
    }

    override fun getNewWidth(): Int {
        return mNewWidth
    }

    /**
     * 重新设置高度
     */
    fun newHeight(newHeight: Int) {
        this.mNewHeight = newHeight
        DialogSizeHelper.dealSize(
            this,
            if (mNewWidth == DialogSize.NO_SET) initDialogWidth() else mNewWidth,
            newHeight
        )
    }

    /**
     * 重新设置宽度
     */
    fun newWidth(newWidth: Int) {
        this.mNewWidth = newWidth
        DialogSizeHelper.dealSize(
            this,
            newWidth,
            if (mNewHeight == DialogSize.NO_SET) initDialogHeight() else mNewHeight
        )
    }

    override fun show() {
        onDialogPrepare()
        super.show()
        onDialogOpen()
    }

    fun show(context: Any?) {
        show()
    }

    override fun dismiss() {
        super.dismiss()
        onDialogClose()
    }
}