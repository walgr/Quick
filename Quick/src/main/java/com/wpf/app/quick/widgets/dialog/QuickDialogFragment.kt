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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.wpf.app.quick.helper.DialogSizeHelper
import com.wpf.app.quickbind.QuickBind

/**
 * Created by 王朋飞 on 2022/6/16.
 */
abstract class QuickDialogFragment @JvmOverloads constructor(
    @LayoutRes
    var layoutId: Int = 0,
    var layoutView: View? = null
) : DialogFragment(), DialogSize, DialogLifecycle {

    override fun onStart() {
        super.onStart()
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(
            getRealContext()!!,
            if (initDialogStyle() == DialogSize.NO_SET) this.theme else initDialogStyle()
        )
        val window = dialog.window
        if (window != null) {
            if (initDialogAnim() != DialogSize.NO_SET) {
                window.setWindowAnimations(initDialogAnim())
            }
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
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
        QuickBind.bind(this)
        initView(view)
        onDialogOpen()
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

    override fun getWindow(): Window? {
        return dialog?.window
    }

    override fun getView(): View? {
        return super.getView()
    }

    override fun getContext(): Context {
        return super.getContext()!!
    }
}