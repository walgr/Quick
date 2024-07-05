package com.wpf.app.quickwidget.bottomsheet

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wpf.app.quickdialog.listeners.QuickContext
import com.wpf.app.quickdialog.listeners.SheetInit
import com.wpf.app.quickutil.helper.generic.forceTo


/**
 * Created by 王朋飞 on 2022/6/21.
 */
@Suppress("LeakingThis")
open class QuickBottomSheetView(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(mContext, attrs, defStyleAttr), SheetInit, QuickContext {

    init {
        initView()
    }

    @CallSuper
    open fun initView() {
        orientation = VERTICAL
        dealSize()
        background = ColorDrawable(Color.TRANSPARENT)
        post {
            initBottomSheet()
            setLayoutParam()
            height = initViewMaxHeight()
            visibility = View.VISIBLE
        }
        visibility = View.GONE
    }

    open fun setLayoutParam() {
        var layoutParams: ViewGroup.LayoutParams = layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            val layoutParamsC: CoordinatorLayout.LayoutParams =
                layoutParams
            layoutParamsC.behavior = mBehavior
        } else {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mScreenHeight)
            if (mBehavior != null) {
                layoutParams.forceTo<CoordinatorLayout.LayoutParams>().behavior = mBehavior
            }
            setLayoutParams(layoutParams)
        }
    }

    fun setHeight(height: Int) {
        if (height < -1) return
        var layoutParams: ViewGroup.LayoutParams = layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            val layoutParamsC: CoordinatorLayout.LayoutParams =
                layoutParams
            layoutParamsC.height = height
            setLayoutParams(layoutParamsC)
        } else {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            setLayoutParams(layoutParams)
        }
    }

    protected var mScreenWidth = 0
    protected var mScreenHeight = 0

    open fun dealSize() {
        val size: Point = getScreenSize()
        mScreenWidth = size.x
        mScreenHeight = size.y
    }

    open fun initViewMaxHeight(): Int {
        return 0
    }

    private var mBehavior: BottomSheetBehavior<View>? = null
    fun getBehavior() = mBehavior
    open fun initBottomSheet() {
        mBehavior = if (canScroll()) {
            BottomSheetBehavior()
        } else {
            NoScrollBottomSheetBehavior()
        }
        setBottomSheet()
    }

    @CallSuper
    open fun setBottomSheet() {
        mBehavior?.isHideable = hideAble()
        mBehavior?.state = initSheetState()
        mBehavior?.peekHeight = initPeekHeight()
    }

    override fun getViewContext(): Context {
        return context
    }
}