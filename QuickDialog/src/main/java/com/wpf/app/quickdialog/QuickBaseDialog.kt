package com.wpf.app.quickdialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import com.wpf.app.base.QuickView
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickdialog.helper.DialogSizeHelper
import com.wpf.app.quickdialog.listeners.DialogLifecycle
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickdialog.minAndMaxLimit.SizeLimitViewGroup
import com.wpf.app.quickutil.run.RunOnContext
import com.wpf.app.quickutil.helper.InitViewHelper

/**
 * Created by 王朋飞 on 2022/6/16.
 */
open class QuickBaseDialog(
    context: Context,
    themeId: Int = 0,
    @LayoutRes private val layoutId: Int = 0,
    private val layoutView: View? = null,
    private val layoutViewInContext: RunOnContext<View>? = null,
) : AppCompatDialog(context, themeId), QuickView, DialogSize, DialogLifecycle {

    public override fun onStart() {
        super.onStart()
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    private var mView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealSize()
        mView = generateContentView(InitViewHelper.init(getViewContext(), layoutId, layoutView, layoutViewInContext))
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
        QuickBindWrap.bind(this)
        initView(mView!!)
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

    open fun generateContentView(view: View): View {
        return view
    }

    open fun initView(view: View) {

    }

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

    override fun dismiss() {
        super.dismiss()
        onDialogClose()
        listener?.onDismiss(this)
    }

    var listener: DialogInterface.OnDismissListener? = null
    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        this.listener = listener
    }

    override fun getViewContext(): Context {
        return context
    }
}