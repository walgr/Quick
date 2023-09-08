//package com.wpf.app.quick.helper.attribute
//
//import android.content.Context
//import android.util.AttributeSet
//import androidx.annotation.DrawableRes
//import com.wpf.app.quick.R
//import com.wpf.app.quick.constant.*
//import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
//
///**
// * Created by 王朋飞 on 2022/4/29.
// * Glide 参数配置帮助类
// */
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