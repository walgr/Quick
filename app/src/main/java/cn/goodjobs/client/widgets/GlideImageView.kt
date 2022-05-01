package cn.goodjobs.client.widgets

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import cn.goodjobs.client.R
import cn.goodjobs.client.helper.GlideAttributeHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.BaseRequestOptions

/**
 * Created by 王朋飞 on 2022/4/29.
 *
 */
open class GlideImageView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributes, defStyleAttr) {

    private var glide: RequestManager

    init {
        attributes?.let {
            glideAttributeHelper = GlideAttributeHelper(context, attributes)
        }
        glide = Glide.with(context)
        loadData()
    }

    private var glideRequestManager: RequestBuilder<Drawable>? = null
    private lateinit var glideAttributeHelper: GlideAttributeHelper

    private fun loadData() {
        if (checkContext()) return
        glideAttributeHelper.orderStyleableId.forEach {
            if (it == R.styleable.GlideImageView_loadUrl) {
                loadUrl()
            }
            if (it == R.styleable.GlideImageView_scaleType) {
                if (glideAttributeHelper.scaleType == ScaleType.CENTER_CROP) centerCrop()
                if (glideAttributeHelper.scaleType == ScaleType.FIT_CENTER) fitCenter()
            }
        }
        glideRequestManager?.into(this)
    }

    private fun loadUrl() {
        if (checkContext()) return
        if (glideAttributeHelper.loadUrl.isEmpty()) return
        glideRequestManager = if (glideRequestManager == null) glide.load(glideAttributeHelper.loadUrl)
        else glideRequestManager?.load(glideAttributeHelper.loadUrl)
    }

    private fun centerCrop() {
        if (checkContext()) return
        glideRequestManager = glideRequestManager?.centerCrop()
    }

    private fun fitCenter() {
        if (checkContext()) return
        glideRequestManager = glideRequestManager?.fitCenter()
    }

    private fun checkContext(): Boolean {
        return (context as Activity).isDestroyed || (context as Activity).isFinishing
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        glideAttributeHelper.recycle()
    }
}