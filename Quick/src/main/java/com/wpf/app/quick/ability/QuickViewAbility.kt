package com.wpf.app.quick.ability

import android.app.Activity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quick.activity.viewmodel.ViewLifecycle
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.Unique
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo


fun <T : QuickActivityAbility> MutableList<T>.with(others: List<T>): MutableList<T> {
    others.filter { it is Unique }.map { it.getPrimeKey() }.forEach { otherPrimeKey ->
        this.remove(this.find { it.getPrimeKey() == otherPrimeKey })
    }
    this.addAll(others)
    return this
}

fun <T : QuickActivityAbility> MutableList<T>.with(other: T): MutableList<T> {
    if (other is Unique) {
        this.remove(this.find { it.getPrimeKey() == other.getPrimeKey() })
    }
    this.add(other)
    return this
}

fun setContentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (QuickView.(view: View) -> Unit)? = null,
): MutableList<QuickActivityAbility> {
    return mutableListOf(object : QuickInflateViewAbility {
        override fun layoutId(): Int {
            return layoutId
        }

        override fun layoutView(): View? {
            return layoutView
        }

        override fun layoutViewInContext(): RunOnContext<View>? {
            return layoutViewInContext
        }

        override fun beforeDealContentView(owner: ViewModelStoreOwner, view: View) {
            super.beforeDealContentView(owner, view)
            contentBuilder?.invoke(owner.forceTo(), view)
        }
    })
}

inline fun <reified VM : QuickViewModel<out QuickView>> viewModel(
    noinline vmBuilder: VM.() -> Unit
): MutableList<QuickActivityAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    override fun getViewModel() = viewModel

    override fun dealContentView(owner: ViewModelStoreOwner, view: View) {
        super.dealContentView(owner, view)
        val viewModelCls = VM::class.java
        val activity = owner.forceTo<Activity>()
        viewModel = ViewModelProvider(
            owner, ViewModelProvider.AndroidViewModelFactory(activity.application)
        )[viewModelCls]
        vmBuilder.invoke(viewModel!!)
        QuickBindWrap.bind(activity, viewModel)
        viewModel.asTo<QuickViewModel<QuickView>>()?.onViewCreated(activity.forceTo())
    }
})

inline fun <reified VB : ViewDataBinding> binding(
    noinline vbBuilder: (VB.() -> Unit)? = null
): MutableList<QuickActivityAbility> = modelBinding<QuickVBModel<VB>, VB>(
    vbBuilder = vbBuilder
)

inline fun <reified VM : QuickVBModel<VB>, reified VB : ViewDataBinding> modelBinding(
    noinline vmBuilder: (VM.() -> Unit)? = null,
    noinline vbBuilder: (VB.() -> Unit)? = null,
    noinline mbBuilder: (VM.(vb: VB) -> Unit)? = null
): MutableList<QuickActivityAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    private var viewBinding: VB? = null
    override fun dealContentView(owner: ViewModelStoreOwner, view: View) {
        super.dealContentView(owner, view)
        val viewModelCls = VM::class.java
        val activity = owner.forceTo<QuickActivity>()
        if (viewModelCls != QuickVBModel::class.java) {
            viewModel = ViewModelProvider(
                owner, ViewModelProvider.AndroidViewModelFactory(activity.application)
            )[viewModelCls]
            vmBuilder?.invoke(viewModel!!)
        }
        if (viewBinding == null && VB::class.java != ViewDataBinding::class.java) {
            viewBinding = DataBindingUtil.bind(activity.getMyContentView()!!)
            viewBinding?.lifecycleOwner = activity.forceTo()
        }
        viewBinding?.setVariable(BRConstant.viewModel, viewModel)
        viewBinding?.executePendingBindings()
        viewBinding?.let {
            viewModel?.mViewBinding = it
            vbBuilder?.invoke(it)
        }
        QuickBindWrap.bind(activity, viewModel)
        viewModel.asTo<VM>()?.onBindingCreated(viewBinding!!)
        mbBuilder?.invoke(viewModel!!, viewBinding!!)
    }

    override fun getViewModel() = viewModel
})

fun dealBind(
    dealBind: Boolean = true
): MutableList<QuickActivityAbility> {
    return mutableListOf(object : QuickDealBindAbility {
        override fun dealBind() = dealBind
    })
}

fun inConstraintLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (ConstraintLayout.() -> View?)? = null,
): MutableList<QuickActivityAbility> {
    return setContentView(layoutViewInContext = runOnContext { context ->
        ConstraintLayout(context).apply {
            layoutParams = matchLayoutParams
            if (layoutViewInContext != null || layoutView != null || layoutId != 0) {
                addView(
                    InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext),
                    matchLayoutParams
                )
            }
        }
    }, contentBuilder = {
        setMyContentView(
            contentBuilder?.invoke(it.forceTo<ConstraintLayout>()) ?: it.forceTo<ConstraintLayout>()
                .getChildAt(0)
        )
    })
}

fun inRelativeLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (RelativeLayout.() -> View?)? = null,
): MutableList<QuickActivityAbility> {
    return setContentView(layoutViewInContext = runOnContext { context ->
        RelativeLayout(context).apply {
            layoutParams = matchLayoutParams
            if (layoutViewInContext != null || layoutView != null || layoutId != 0) {
                addView(
                    InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext),
                    matchLayoutParams
                )
            }
        }
    }, contentBuilder = {
        setMyContentView(
            contentBuilder?.invoke(it.forceTo<RelativeLayout>()) ?: it.forceTo<RelativeLayout>()
                .getChildAt(0)
        )
    })
}

fun inLinearLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (LinearLayout.() -> View?)? = null,
): MutableList<QuickActivityAbility> {
    return setContentView(layoutViewInContext = runOnContext { context ->
        LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchLayoutParams
            if (layoutViewInContext != null || layoutView != null || layoutId != 0) {
                addView(
                    InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext),
                    matchLayoutParams
                )
            }
        }
    }, contentBuilder = {
        setMyContentView(
            contentBuilder?.invoke(it.forceTo<LinearLayout>()) ?: it.forceTo<LinearLayout>()
                .getChildAt(0)
        )
    })
}

interface QuickActivityAbility {
    fun getPrimeKey(): String

    fun dealContentView(owner: ViewModelStoreOwner, view: View) {

    }

    fun initView(view: View) {

    }
}

interface QuickInflateViewAbility : QuickActivityAbility, Unique {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null
    fun layoutViewInContext(): RunOnContext<View>? = null

    fun beforeDealContentView(owner: ViewModelStoreOwner, view: View) {

    }
}

interface QuickDealBindAbility : QuickActivityAbility, Unique {
    override fun getPrimeKey() = "dealBind"
    fun dealBind(): Boolean = true
}

interface QuickVMAbility<VM : ViewModel> : QuickActivityAbility, Unique, ViewLifecycle,
    BindViewModel<VM> {
    override fun getPrimeKey() = "viewModel"
}