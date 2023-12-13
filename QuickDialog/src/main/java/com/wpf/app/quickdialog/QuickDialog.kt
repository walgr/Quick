package com.wpf.app.quickdialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickdialog.helper.DialogSizeHelper
import com.wpf.app.quickdialog.listeners.DialogLifecycle
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickdialog.minAndMaxLimit.MinAndMaxLimit
import com.wpf.app.quickdialog.minAndMaxLimit.SizeLimitViewGroup

/**
 * Created by 王朋飞 on 2022/6/16.
 */
abstract class QuickDialog : Dialog, DialogSize, DialogLifecycle {

    @LayoutRes
    var layoutId: Int = 0
    var layoutView: View? = null

    constructor(
        mContext: Context,
        themeId: Int = 0,
        @LayoutRes layoutId: Int = 0,
        layoutView: View? = null
    ) : super(mContext, themeId) {
        this.layoutId = layoutId
        this.layoutView = layoutView
    }

    constructor(
        mContext: Context,
        cancelable: Boolean = false,
        cancelListener: DialogInterface.OnCancelListener? = null,
        @LayoutRes layoutId: Int = 0,
        layoutView: View? = null
    ) : super(mContext, cancelable, cancelListener) {
        this.layoutId = layoutId
        this.layoutView = layoutView
    }

    public override fun onStart() {
        super.onStart()
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    private var mView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealSize()
        mView = layoutView ?: LayoutInflater.from(getViewContext()).inflate(layoutId, null)
        if (initDialogAdaptiveHeight()) {
            mView = SizeLimitViewGroup(getViewContext()).apply {
                addView(mView)
            }
        }
        setContentView(mView!!)
        if (window != null) {
            if (initDialogAnim() != DialogSize.NO_SET) {
                window!!.setWindowAnimations(initDialogAnim())
            }
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.decorView.setPadding(0, 0, 0, 0)
        }
        QuickBind.bind(this)
        initView(mView)
    }

    private var mScreenWidth = 0
    private var mScreenHeight = 0

    open fun dealSize() {
        val size = getScreenSize()
        mScreenWidth = size.x
        mScreenHeight = size.y
    }

    override fun getView(): View? {
        return mView
    }

    abstract fun initView(view: View?)

    open var mNewWidth = DialogSize.NO_SET
    open var mNewHeight = DialogSize.NO_SET

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

    override fun getViewContext(): Context {
        return context
    }
}