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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wpf.app.quickdialog.helper.DialogSheetHelper
import com.wpf.app.quickdialog.helper.DialogSizeHelper
import com.wpf.app.quickdialog.listeners.DialogLifecycle
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickdialog.minAndMaxLimit.SizeLimitViewGroup
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.bind.BindBaseFragment
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.network.RequestCoroutineScope
import com.wpf.app.quickutil.utils.SheetInit
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/6/21.
 */
open class QuickBaseBottomSheetDialogFragment @JvmOverloads constructor(
    @StyleRes val themeId: Int = R.style.TranslateBottomSheetDialogTheme,
    @LayoutRes open val layoutId: Int = 0,
    var layoutView: View? = null,
    var layoutViewCreate: (Context.() -> View)? = null,
) : BottomSheetDialogFragment(), BindBaseFragment, RequestCoroutineScope, DialogSize,
    DialogLifecycle, SheetInit, Bind, Quick {

    override var jobManager: MutableList<Job> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateWithSavedInstanceState(savedInstanceState)
        registerForActivityResult()
    }


    open fun onCreateWithSavedInstanceState(savedInstanceState: Bundle?) {

    }

    private var launcher: ActivityResultLauncher<Intent>? = null
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
        val dialog = super.onCreateDialog(savedInstanceState)
        if (initDialogAnimStyle() != 0) {
            getWindow()?.setWindowAnimations(initDialogAnimStyle())
        }
        getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getWindow()?.decorView?.setPadding(0, 0, 0, 0)
        return dialog
    }

    private var mView: View? = null
    override fun getView(): View? {
        return mView
    }
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

    private var mBehavior: BottomSheetBehavior<View>? = null
    fun getBehavior() = mBehavior
    override fun onStart() {
        super.onStart()
        mBehavior = DialogSheetHelper.dealSheet(this)
        DialogSizeHelper.dealSize(this, initDialogWidth(), initDialogHeight())
    }

    override fun onResume() {
        super.onResume()
        onDialogShow()
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

    override fun getWindow(): Window? {
        return dialog?.window
    }

    override fun getNewWidth(): Int {
        return mNewWidth
    }

    override fun getNewHeight(): Int {
        return mNewHeight
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected var mNewWidth = 0
    @Suppress("MemberVisibilityCanBePrivate")
    protected var mNewHeight = 0

    /**
     * 重新设置大小
     */
    fun newSize(newWidth: Int = 0, newHeight: Int =0) {
        if (mNewWidth != 0) {
            this.mNewWidth = newWidth
        }
        if (mNewHeight != 0) {
            this.mNewHeight = newHeight
        }
        DialogSizeHelper.dealSize(
            this,
            if (mNewWidth == 0) initDialogWidth() else mNewWidth,
            if (mNewHeight == 0) initDialogHeight() else mNewHeight
        )
    }

    override fun getLifecycleDialog(): Dialog {
        return super.getDialog()!!
    }
    override var funcPrepare: (() -> Unit)? = null
    override var funcShow: (Dialog.() -> Unit)? = null
    override var funcDismiss: (Dialog.() -> Unit)? = null

    open fun show(context: Any): QuickBaseBottomSheetDialogFragment {
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

    override fun getViewContext(): Context {
        return super.requireContext()
    }

    override fun getTheme(): Int {
        return themeId
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
        mView = null
        launcher = null
    }
}