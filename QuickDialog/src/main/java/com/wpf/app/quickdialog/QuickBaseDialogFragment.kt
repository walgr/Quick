package com.wpf.app.quickdialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.wpf.app.base.NO_SET
import com.wpf.app.base.Quick
import com.wpf.app.base.bind.Bind
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickdialog.helper.DialogSizeHelper
import com.wpf.app.quickdialog.listeners.DialogLifecycle
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickdialog.minAndMaxLimit.SizeLimitViewGroup
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.helper.InitViewHelper
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/6/16.
 */
open class QuickBaseDialogFragment @JvmOverloads constructor(
    @StyleRes val themeId: Int = 0,
    @LayoutRes var layoutId: Int = 0,
    var layoutView: View? = null,
    var layoutViewCreate: (Context.() -> View)? = null,
) : DialogFragment(), BindBaseFragment, RequestCoroutineScope, DialogSize, DialogLifecycle, Bind,
    Quick {

    override var jobManager: MutableList<Job> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult()
    }

    private var launcher: ActivityResultLauncher<Intent>? = null
    private var resultRegister: ActivityResultRegistry? = null
    private var resultCallback: ActivityResultCallback<ActivityResult>? = null
    open fun registerForActivityResult(
        resultCallback: ActivityResultCallback<ActivityResult>,
    ) {
        this.resultCallback = resultCallback
    }

    private fun registerForActivityResult() {
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            resultCallback?.onActivityResult(result)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(getRealContext()!!, themeId)
        val window = dialog.window
        if (window != null) {
            if (initDialogAnimStyle() != DialogSize.NO_SET) {
                window.setWindowAnimations(initDialogAnimStyle())
            }
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.decorView.setPadding(0, 0, 0, 0)
        }
        return dialog
    }

    private var mView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (mView == null) {
            mView = generateContentView(
                InitViewHelper.init(
                    inflater.context,
                    layoutId,
                    layoutView,
                    layoutViewCreate
                )
            )
        }
        if (initDialogAdaptiveHeight()) {
            mView = SizeLimitViewGroup(getViewContext()).apply {
                addView(mView)
            }
        }
        return mView
    }

    open fun generateContentView(view: View): View {
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dealSize()
        QuickBindWrap.bind(this)
        initView(mView!!)
    }

    override fun onStart() {
        super.onStart()
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    override fun onResume() {
        super.onResume()
        onDialogShow()
    }

    open fun initView(view: View) {}

    override fun getView(): View? {
        return mView
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
        return super.getDialog()!!
    }
    override var funcPrepare: (() -> Unit)? = null
    override var funcShow: (Dialog.() -> Unit)? = null
    override var funcDismiss: (Dialog.() -> Unit)? = null

    open fun show(context: Any): QuickBaseDialogFragment {
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
        return this
    }

    override fun dismiss() {
        super.dismiss()
        onDialogDismiss()
    }

    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
        onDialogDismiss()
    }

    override fun getWindow(): Window? {
        return dialog?.window
    }

    override fun getViewContext(): Context {
        return super.requireContext()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
        mView = null
        launcher = null
    }
}