package com.wpf.app.quick.demo.widgets.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickwidget.emptyview.BaseEmptyView
import com.wpf.app.quickwidget.emptyview.CustomError
import com.wpf.app.quickwidget.emptyview.EmptyDataError
import com.wpf.app.quickwidget.emptyview.EmptyViewState
import com.wpf.app.quickwidget.emptyview.ExceptionError
import com.wpf.app.quickwidget.emptyview.Loading
import com.wpf.app.quickwidget.emptyview.NetError
import com.wpf.app.quickwidget.emptyview.NoError
import com.wpf.app.quickwidget.emptyview.Success

class TestEmptyView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BaseEmptyView(mContext, attrs, defStyleAttr, R.layout.empty_layout, curState = Loading) {

    private var progress: ProgressBar? = null
    private var errorGroup: ViewGroup? = null
    private var mImageView: ImageView? = null
    private var title: TextView? = null
    private var info: TextView? = null
    private var btnNext: TextView? = null

    override fun initView(view: View) {
        mImageView = view.findViewById(R.id.imageTop)
        title = view.findViewById(R.id.title)
        info = view.findViewById(R.id.info)
        btnNext = view.findViewById(R.id.btnNext)
        progress = view.findViewById(R.id.progress)
        errorGroup = view.findViewById(R.id.errorGroup)
        changeState(curState)
    }

    override fun changeState(state: EmptyViewState) {
        super.changeState(state)
        if (state is NoError) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
            progress?.visibility = View.GONE
            errorGroup?.visibility = View.VISIBLE
            when (state) {
                is ExceptionError -> {

                }
                is Loading -> {
                    errorGroup?.visibility = View.GONE
                    progress?.visibility = View.VISIBLE
                }
                is EmptyDataError -> {
                    title?.text = "数据异常"
                    info?.text = "暂无数据"
                    btnNext?.text = "刷新重试"
                }
                is NetError -> {
                    title?.text = "网络异常"
                    info?.text = "暂无数据"
                    btnNext?.text = "刷新重试"
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