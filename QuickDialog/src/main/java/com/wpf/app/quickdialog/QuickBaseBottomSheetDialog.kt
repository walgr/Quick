package com.wpf.app.quickdialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wpf.app.base.NO_SET
import com.wpf.app.base.Quick
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickdialog.helper.DialogSheetHelper
import com.wpf.app.quickdialog.helper.DialogSizeHelper
import com.wpf.app.quickdialog.listeners.DialogLifecycle
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickdialog.listeners.SheetInit
import com.wpf.app.quickdialog.minAndMaxLimit.SizeLimitViewGroup
import com.wpf.app.quickutil.helper.InitViewHelper

/**
 * Created by 王朋飞 on 2022/6/21.
 */
open class QuickBaseBottomSheetDialog(
    context: Context,
    @StyleRes val themeId: Int = 0,
    @LayoutRes private val layoutId: Int = 0,
    private val layoutView: View? = null,
    private var layoutViewCreate: (Context.() -> View)? = null,
) : BottomSheetDialog(context, true, null), SheetInit, DialogSize, DialogLifecycle, Quick {

    private var mView: View? = null
    override fun getView(): View? {
        return mView
    }

    private var mBehavior: BottomSheetBehavior<View>? = null

    @CallSuper
    override fun onStart() {
        super.onStart()
        mBehavior = DialogSheetHelper.dealSheet(this)
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealSize()
        mView = generateContentView(
            InitViewHelper.init(
                context,
                layoutId,
                layoutView,
                layoutViewCreate
            )
        )
        if (initDialogAdaptiveHeight()) {
            mView = SizeLimitViewGroup(getViewContext()).apply {
                addView(mView)
            }
        }
        setContentView(mView!!)
        val window = window
        if (window != null) {
            if (initDialogAnimStyle() != DialogSize.NO_SET) {
                window.setWindowAnimations(initDialogAnimStyle())
            }
            window.decorView.setPadding(0, 0, 0, 0)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        QuickBindWrap.bind(this)
        initView(mView!!)
    }

    open fun generateContentView(view: View): View {
        return view
    }

    open fun initView(view: View) {}

    @Suppress("MemberVisibilityCanBePrivate")
    protected var mScreenWidth = 0
    @Suppress("MemberVisibilityCanBePrivate")
    protected var mScreenHeight = 0

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun dealSize() {
        val size = getScreenSize()
        mScreenWidth = size.x
        mScreenHeight = size.y
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected var mNewWidth = DialogSize.NO_SET
    @Suppress("MemberVisibilityCanBePrivate")
    protected var mNewHeight = DialogSize.NO_SET

    override fun getNewHeight(): Int {
        return mNewHeight
    }

    override fun getNewWidth(): Int {
        return mNewWidth
    }

    /**
     * 重新设置大小
     */
    fun newSize(newWidth: Int = NO_SET, newHeight: Int = NO_SET) {
        if (mNewWidth != DialogSize.NO_SET) {
            this.mNewWidth = newWidth
        }
        if (mNewHeight != DialogSize.NO_SET) {
            this.mNewHeight = newHeight
        }
        DialogSizeHelper.dealSize(
            this,
            if (mNewWidth == DialogSize.NO_SET) initDialogWidth() else mNewWidth,
            if (mNewHeight == DialogSize.NO_SET) initDialogHeight() else mNewHeight
        )
    }

    override fun getLifecycleDialog(): Dialog {
        return this
    }
    override var funcPrepare: (() -> Unit)? = null
    override var funcShow: (Dialog.() -> Unit)? = null
    override var funcDismiss: (Dialog.() -> Unit)? = null
    override fun show() {
        onDialogPrepare()
        super.show()
        onDialogShow()
    }

    open fun show(any: Any): QuickBaseBottomSheetDialog {
        show()
        return this
    }

    override fun dismiss() {
        super.dismiss()
        onDialogDismiss()
        listener?.onDismiss(this)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    var listener: DialogInterface.OnDismissListener? = null
    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        this.listener = listener
    }

    override fun getViewContext(): Context {
        return context
    }
}