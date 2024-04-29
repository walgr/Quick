package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.base.bind.Bind
import com.wpf.app.quickutil.run.RunOnContext
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/8/23.
 *
 */
abstract class QuickItemView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @LayoutRes private val layoutId: Int = 0,
    private val layoutView: RunOnContext<View>? = null,
    open var viewType: Int = 0,
) : View(mContext, attributeSet, defStyleAttr), Bind {

    init {
        init()
    }

    private fun init() {
        initViewType()
        initView()
    }

    open fun initViewType() {
        if (viewType == 0) {
            viewType = abs(javaClass.name.hashCode())
        }
    }

    private var mView: View? = null
    var position: Int = -1

    open fun initView() {
        if (layoutId == 0 && layoutView == null) return
        mView = if (layoutId != 0) {
            inflate(context, this.layoutId, null)
        } else {
            layoutView?.run(context)
        }
    }

    private fun addToParent() {
        val parentGroup = parent as? ViewGroup ?: return
        position = parentGroup.indexOfChild(this)
        parentGroup.removeView(this)
        getView()?.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
        parentGroup.addView(mView, position)
        onCreateViewHolder()
        onBindViewHolder(position)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        getView()?.setPadding(left, top, right, bottom)
    }

    abstract fun onCreateViewHolder()

    abstract fun onBindViewHolder(position: Int)

    override fun getView(): View? {
        return mView
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        addToParent()
        getView()?.measure(widthMeasureSpec, heightMeasureSpec)
        val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
        val viewMeasureWidth = getView()?.measuredWidth ?: 0
        val viewMeasureHeight = getView()?.measuredHeight ?: 0
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
            MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        getView()?.layout(left, top, right, bottom)
        super.onLayout(changed, left, top, right, bottom)
    }

    //预览可见
    override fun onDraw(canvas: Canvas) {
        getView()?.draw(canvas)
        super.onDraw(canvas)
    }
}