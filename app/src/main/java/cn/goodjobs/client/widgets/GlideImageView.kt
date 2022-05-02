package cn.goodjobs.client.widgets

import android.app.Activity
import android.content.Context
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import cn.goodjobs.client.helper.GlideAttributeHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * Created by 王朋飞 on 2022/4/29.
 *
 */
open class GlideImageView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributes, defStyleAttr) {

    private lateinit var glideAttributeHelper: GlideAttributeHelper
    private var glideRequestManager: RequestBuilder<*>? = null
    private var glide: RequestManager

    init {
        attributes?.let {
            glideAttributeHelper = GlideAttributeHelper(context, attributes)
        }
        glide = Glide.with(context)
        loadData()
    }

    private fun loadData() {
        if (checkContext()) return
        loadTranscodeType()
        loadScaleType()
        loadUrl()
        placeholder()
        error()
        roundedCorners()
        glideRequestManager?.into(this)
    }

    private fun loadTranscodeType() {
        when(glideAttributeHelper.transcodeType) {
            GlideAttributeHelper.asDrawable -> asDrawable()
            GlideAttributeHelper.asBitmap -> asBitmap()
            GlideAttributeHelper.asGif -> asGif()
        }
    }

    fun asBitmap() {
        glideRequestManager = glide.asBitmap()
    }

    fun asDrawable() {
        glideRequestManager = glide.asDrawable()
    }

    fun asGif() {
        glideRequestManager = glide.asGif()
    }

    fun loadUrl() {
        if (glideAttributeHelper.loadUrl?.isEmpty() == true) return
        glideRequestManager = glideRequestManager?.load(glideAttributeHelper.loadUrl)
    }

    fun placeholder() {
        glideRequestManager = glideRequestManager?.placeholder(glideAttributeHelper.placeholder)
    }

    fun error() {
        glideRequestManager = glideRequestManager?.error(glideAttributeHelper.error)
    }

    fun centerCrop() {
        glideRequestManager = glideRequestManager?.centerCrop()
    }

    private fun loadScaleType() {
        when(glideAttributeHelper.scaleType) {
            GlideAttributeHelper.FIT_CENTER -> fitCenter()
            GlideAttributeHelper.CENTER_CROP -> centerCrop()
            GlideAttributeHelper.CENTER_INSIDE -> centerInside()
            GlideAttributeHelper.CIRCLE_CROP -> circleCrop()
        }
    }

    fun fitCenter() {
        glideRequestManager = glideRequestManager?.fitCenter()
    }

    fun centerInside() {
        glideRequestManager = glideRequestManager?.centerInside()
    }

    fun circleCrop() {
        glideRequestManager = glideRequestManager?.circleCrop()
    }

    fun roundedCorners() {
        if (glideAttributeHelper.roundedCorners <= 0) return
        glideRequestManager = glideRequestManager?.transform(RoundedCorners(glideAttributeHelper.roundedCorners))
    }

    fun setAllRound() {
        glideRequestManager = glideRequestManager?.transform()
    }

    fun checkContext(): Boolean {
        return (context as Activity).isDestroyed || (context as Activity).isFinishing
    }

    /**
     * 适配view大小可调节的系统，例如winAndroid子系统
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        glideAttributeHelper.recycle()
    }
}