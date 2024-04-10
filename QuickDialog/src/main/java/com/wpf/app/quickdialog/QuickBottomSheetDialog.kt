package com.wpf.app.quickdialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickdialog.helper.DialogSheetHelper
import com.wpf.app.quickdialog.helper.DialogSizeHelper
import com.wpf.app.quickdialog.listeners.DialogLifecycle
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickdialog.listeners.SheetInit
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.run.RunOnContext

/**
 * Created by 王朋飞 on 2022/6/21.
 */
open class QuickBottomSheetDialog(
    context: Context,
    themeId: Int = 0,
    @LayoutRes private val layoutId: Int = 0,
    private val layoutView: View? = null,
    private val layoutViewInContext: RunOnContext<View>? = null,
) : BottomSheetDialog(context, themeId), DialogSize, DialogLifecycle, SheetInit {

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
        mView = generateContentView(InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext))
        setContentView(mView!!)
        val window = window
        if (window != null) {
            if (initDialogAnim() != DialogSize.NO_SET) {
                window.setWindowAnimations(initDialogAnim())
            }
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.decorView.setPadding(0, 0, 0, 0)
        }
        QuickBindWrap.bind(this)
        initView(mView!!)
    }

    open fun generateContentView(view: View): View {
        return view
    }

    fun initView(view: View) {

    }

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