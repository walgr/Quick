package com.wpf.app.quick.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wpf.app.quick.helper.DialogSheetHelper
import com.wpf.app.quick.helper.DialogSizeHelper

/**
 * Created by 王朋飞 on 2022/6/21.
 */
open class QuickBottomSheetDialogFragment @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null
) : BottomSheetDialogFragment(),
    DialogSize, DialogLifecycle, SheetInit {

    protected var mContext: Context? = null
    override fun getRealContext(): Context? {
        return mContext
    }

    private var mBehavior: BottomSheetBehavior<View>? = null
    override fun onStart() {
        super.onStart()
        mBehavior = DialogSheetHelper.dealSheet(this)
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.mContext = getRealActivity()
        val dialog = BottomSheetDialog(
            context, if (initDialogStyle() == DialogSize.NO_SET) this.theme else initDialogStyle()
        )
        if (initDialogAnim() != DialogSize.NO_SET) {
            getWindow()?.setWindowAnimations(initDialogAnim())
        }
        getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getWindow()?.decorView?.setPadding(0, 0, 0, 0)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return if (layoutView != null) {
            layoutView!!
        } else {
            inflater.inflate(layoutId, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dealSize()
        initView(view)
        onDialogOpen()
    }

    protected var mScreenWidth = 0
    protected var mScreenHeight = 0

    protected fun dealSize() {
        val size = getScreenSize()
        mScreenWidth = size.x
        mScreenHeight = size.y
    }

    fun initView(view: View?) {}

    override fun getWindow(): Window? {
        return dialog?.window
    }

    override fun getNewWidth(): Int {
        return mNewWidth
    }

    override fun getNewHeight(): Int {
        return mNewHeight
    }

    protected var mNewWidth = DialogSize.NO_SET
    protected var mNewHeight = DialogSize.NO_SET

    override fun getContext(): Context {
        return mContext!!
    }

    /**
     * 重新设置高度
     */
    fun newHeight(newHeight: Int) {
        this.mNewHeight = newHeight
        DialogSizeHelper.dealSize(
            this,
            if (mNewHeight == 0) initDialogWidth() else mNewHeight,
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
            if (mNewWidth == 0) initDialogHeight() else mNewWidth
        )
    }

    fun show(context: Any) {
        onDialogPrepare()
        if (context is AppCompatActivity) {
            show(
                context.supportFragmentManager,
                javaClass.name + System.currentTimeMillis()
            )
        } else if (context is Fragment) {
            show(
                context.childFragmentManager,
                javaClass.name + System.currentTimeMillis()
            )
        }
    }

    override fun dismiss() {
        super.dismiss()
        onDialogClose()
    }

    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
        onDialogClose()
    }
}