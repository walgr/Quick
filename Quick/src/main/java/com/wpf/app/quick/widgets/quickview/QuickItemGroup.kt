package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.interfaces.Bind
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
abstract class QuickItemGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @LayoutRes
    private val layoutId: Int,
    open var viewType: Int = 0,
) : ViewGroup(mContext, attributeSet, defStyleAttr), Bind {

    private var mView: View? = null
    var position: Int = -1

    init {
        initView()
        initViewType()
    }

    open fun initViewType() {
        if (viewType == 0) {
            viewType = abs(javaClass.name.hashCode())
        }
    }

    open fun initView() {
        mView = inflate(context, this.layoutId, this)
        post {
            val parentGroup = parent as? ViewGroup ?: return@post
            position = parentGroup.indexOfChild(this)
            parentGroup.addView(mView, position)
            visibility = GONE
            onCreateViewHolder()
            onBindViewHolder(position)
        }
    }

    abstract fun onCreateViewHolder()

    abstract fun onBindViewHolder(position: Int)

    override fun getView(): View? {
        return mView
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        getView()?.measure(widthMeasureSpec, heightMeasureSpec)
        val viewMeasureWidth = getView()?.measuredWidth ?: 0
        val viewMeasureHeight = getView()?.measuredHeight ?: 0
        val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
            MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        getView()?.layout(left, top, right, bottom)

    }

    override fun onDraw(canvas: Canvas?) {
        return getView()?.draw(canvas) ?: super.onDraw(canvas)
    }
}