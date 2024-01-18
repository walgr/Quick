//package com.wpf.app.quick.widgets.image
//
//import android.app.Activity
//import android.content.Context
//import android.util.AttributeSet
//import androidx.appcompat.widget.AppCompatImageView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.RequestBuilder
//import com.bumptech.glide.RequestManager
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import com.wpf.app.quick.constant.*
//import com.wpf.app.quick.helper.attribute.GlideAttributeHelper
//
///**
// * Created by 王朋飞 on 2022/4/29.
// *
// */
//open class GlideImageView @JvmOverloads constructor(
//    context: Context,
//    attributes: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : AppCompatImageView(context, attributes, defStyleAttr) {
//
//    private lateinit var attributeHelper: GlideAttributeHelper
//    private var glideRequestManager: RequestBuilder<*>? = null
//    private var glide: RequestManager
//
//    init {
//        attributes?.let {
//            attributeHelper = GlideAttributeHelper(context, attributes)
//        }
//        glide = Glide.with(context)
//        loadViewAttribute()
//    }
//
//    private fun loadViewAttribute() {
//        if (checkContext()) return
//        loadTranscodeType()
//        loadScaleType()
//        loadUrl()
//        placeholder()
//        error()
//        roundedCorners(attributeHelper.roundedCorners)
//        granularRoundedCorners(
//            attributeHelper.topLeftRadius,
//            attributeHelper.topRightRadius,
//            attributeHelper.bottomRightRadius,
//            attributeHelper.bottomLeftRadius
//        )
//        glideRequestManager?.into(this)
//    }
//
//    private fun loadTranscodeType() {
//        when (attributeHelper.transcodeType) {
//            GlideAttributeHelper.asDrawable -> asDrawable()
//            GlideAttributeHelper.asBitmap -> asBitmap()
//            GlideAttributeHelper.asGif -> asGif()
//        }
//    }
//
//    fun asBitmap() {
//        glideRequestManager = glide.asBitmap()
//    }
//
//    fun asDrawable() {
//        glideRequestManager = glide.asDrawable()
//    }
//
//    fun asGif() {
//        glideRequestManager = glide.asGif()
//    }
//
//    fun loadUrl() {
//        if (attributeHelper.loadUrl?.isEmpty() == true) return
//        glideRequestManager = glideRequestManager?.load(attributeHelper.loadUrl)
//    }
//
//    fun loadUrl(url: String) {
//        glideRequestManager = glideRequestManager?.clone()
//        glideRequestManager?.load(url)
//        glideRequestManager?.into(this)
//    }
//
//    fun placeholder() {
////        glideRequestManager = glideRequestManager?.placeholder(attributeHelper.placeholder)
//    }
//
//    fun error() {
////        glideRequestManager = glideRequestManager?.error(attributeHelper.error)
//    }
//
//    fun centerCrop() {
////        glideRequestManager = glideRequestManager?.centerCrop()
//    }
//
//    private fun loadScaleType() {
//        when (attributeHelper.scaleType) {
//            FIT_CENTER -> fitCenter()
//            CENTER_CROP -> centerCrop()
//            CENTER_INSIDE -> centerInside()
//            CIRCLE_CROP -> circleCrop()
//        }
//    }
//
//    fun fitCenter() {
////        glideRequestManager = glideRequestManager?.fitCenter()
//    }
//
//    fun centerInside() {
////        glideRequestManager = glideRequestManager?.centerInside()
//    }
//
//    fun circleCrop() {
////        glideRequestManager = glideRequestManager?.circleCrop()
//    }
//
//    fun roundedCorners(roundedCorners: Int) {
//        if (roundedCorners <= 0) return
////        glideRequestManager = glideRequestManager?.transform(RoundedCorners(roundedCorners))
//    }
//
//    fun granularRoundedCorners(
//        topLeftRadius: Float,
//        topRightRadius: Float,
//        bottomRightRadius: Float,
//        bottomLeftRadius: Float
//    ) {
//        if (topLeftRadius == 0F && topRightRadius == 0F && bottomRightRadius == 0F && bottomLeftRadius == 0F) return
//        if (topLeftRadius < 0) return
//        if (topRightRadius < 0) return
//        if (bottomRightRadius < 0) return
//        if (bottomLeftRadius < 0) return
////        glideRequestManager = glideRequestManager?.transform(
////            GranularRoundedCorners(
////                topLeftRadius,
////                topRightRadius,
////                bottomRightRadius,
////                bottomLeftRadius
////            )
////        )
//    }
//
//    fun checkContext(): Boolean {
//        return (context as Activity).isDestroyed || (context as Activity).isFinishing
//    }
//
//    /**
//     * 适配view大小可调节的系统，例如winAndroid子系统
//     */
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//    }
//
//    override fun setVisibility(visibility: Int) {
//        super.setVisibility(visibility)
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        attributeHelper.recycle()
//    }
//}

//open class GlideAttributeHelper constructor(
//    context: Context,
//    attributeSet: AttributeSet
//) : AutoGetAttributeHelper(context, attributeSet, R.styleable.GlideImageView) {
//
//    companion object {
//        const val asDrawable = 0
//        const val asBitmap = 1
//        const val asGif = 2
//    }
//
//    var transcodeType:Int? = null
//    get() {
//        return when (field) {
//            0 -> asDrawable
//            1 -> asBitmap
//            2 -> asGif
//            else -> asDrawable
//        }
//    }
//    var scaleType: Int? = null
//    get() {
//        return when (field) {
//            0 -> MATRIX
//            1 -> FIT_XY
//            2 -> FIT_START
//            3 -> FIT_CENTER
//            4 -> FIT_END
//            5 -> CENTER
//            6 -> CENTER_CROP
//            7 -> CENTER_INSIDE
//            8 -> CIRCLE_CROP
//            else -> FIT_CENTER
//        }
//    }
//    var loadUrl: String? = null
//
//    //所有角
//    var roundedCorners = 0
//    //左上圆角
//    var topLeftRadius = 0F
//    //右上圆角
//    var topRightRadius = 0F
//    //左下圆角
//    var bottomLeftRadius = 0F
//    //右下圆角
//    var bottomRightRadius = 0F
//
//    @DrawableRes
//    var placeholder: Int = 0
//
//    @DrawableRes
//    var error: Int = 0
//}