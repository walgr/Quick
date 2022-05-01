package cn.goodjobs.client.helper

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import cn.goodjobs.client.R

/**
 * Created by 王朋飞 on 2022/4/29.
 * Glide 参数配置帮助类
 */
open class GlideAttributeHelper constructor(
    context: Context,
    attributeSet: AttributeSet
) : OrderAttributeHelper(context, attributeSet, R.styleable.GlideImageView) {

    private val sScaleTypeArray = arrayOf(
        ScaleType.MATRIX,
        ScaleType.FIT_XY,
        ScaleType.FIT_START,
        ScaleType.FIT_CENTER,
        ScaleType.FIT_END,
        ScaleType.CENTER,
        ScaleType.CENTER_CROP,
        ScaleType.CENTER_INSIDE
    )

    override fun attributeOrder(): IntArray =
        intArrayOf(
            R.styleable.GlideImageView_loadUrl,
            R.styleable.GlideImageView_scaleType,
        )

    init {
        getAllAttributeValue()
    }

    lateinit var loadUrl: String
    lateinit var scaleType: ScaleType

    private fun getAllAttributeValue() {
        for (id in orderStyleableId) {
            if (id == R.styleable.GlideImageView_loadUrl) {
                loadUrl = getAttribute(id, "")
            }
            if (id == R.styleable.GlideImageView_scaleType) {
                val scaleTypeInt = getAttribute(R.styleable.GlideImageView_scaleType, -1)
                if (scaleTypeInt >= 0) {
                    scaleType = sScaleTypeArray[scaleTypeInt]
                }
            }
        }
    }
}