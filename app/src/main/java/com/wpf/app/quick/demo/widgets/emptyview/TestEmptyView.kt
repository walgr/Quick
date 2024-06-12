package com.wpf.app.quick.demo.widgets.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.wpf.app.quick.demo.R
import com.wpf.app.quickwidget.emptyview.BaseEmptyView
import com.wpf.app.quickwidget.emptyview.EmptyViewState
import com.wpf.app.quickwidget.emptyview.StateEmptyData
import com.wpf.app.quickwidget.emptyview.StateLoading
import com.wpf.app.quickwidget.emptyview.StateNetError
import com.wpf.app.quickwidget.emptyview.StateNoError
import com.wpf.app.quickwidget.emptyview.register

class TestEmptyView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var btnClick: (EmptyViewState.() -> Unit)? = null
) : BaseEmptyView(mContext, attrs, defStyleAttr, R.layout.empty_layout, curState = StateNoError) {

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
        progress?.isVisible = false
        errorGroup?.isVisible = false
        btnNext?.setOnClickListener {
            btnClick?.invoke(curState)
        }
        register<StateLoading> {
            isVisible = true
            progress?.isVisible = this.listIsEmpty
            errorGroup?.isVisible = false
        }
        register<StateEmptyData> {
            isVisible = true
            errorGroup?.isVisible = true
            progress?.isVisible = false
            title?.text = "数据异常"
            info?.text = "暂无数据"
            btnNext?.text = "刷新重试"
        }
        register<StateNetError> {
            isVisible = true
            errorGroup?.isVisible = true
            progress?.isVisible = false
            title?.text = "网络异常"
            info?.text = "暂无数据"
            btnNext?.text = "刷新重试"
        }
    }
}