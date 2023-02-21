package com.wpf.app.quick.demo.widgets.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickutil.widgets.emptyview.*

open class TestEmptyView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    override val layoutId: Int = R.layout.empty_layout,
    override val layoutView: View? = null,
    override var curState: EmptyViewState = Loading()
) : BaseEmptyView(mContext, attrs, defStyleAttr, layoutId, layoutView, curState) {

    private var mImageView: ImageView? = null
    private var title: TextView? = null
    private var info: TextView? = null
    private var btnNext: TextView? = null

    override fun initView(view: View) {
        mImageView = view.findViewById(R.id.imageTop)
        title = view.findViewById(R.id.title)
        info = view.findViewById(R.id.info)
        btnNext = view.findViewById(R.id.btnNext)
        changeState(NoError.curState)
    }

    override fun changeState(state: EmptyViewState) {
        super.changeState(state)
        if (state is NoError) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
            when (state) {
                is ExceptionError -> {

                }
                is Loading -> {

                }
                is NetError -> {

                }
                is Success -> {

                }
                is CustomError -> {

                }
                else -> {}
            }
        }

    }
}