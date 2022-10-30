package com.wpf.app.quick.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.wpf.app.quick.R
import com.wpf.app.quick.helper.ScaleTypeHelper
import com.wpf.app.quick.helper.TypefaceHelper
import com.wpf.app.quick.helper.attribute.MultiFunctionAttributeHelper

/**
 * Created by 王朋飞 on 2022/5/7.
 * 多功能
 *                          Title
 * CheckBox   LeftImageview            RightImage
 *                          SubTitle
 */
class MultiFunctionView @JvmOverloads constructor(
    context: Context,
    private val attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr), CheckView, LifecycleOwner {

    private val mLifecycleRegistry = LifecycleRegistry(this)

    private lateinit var attributeHelper: MultiFunctionAttributeHelper

    override fun onFinishInflate() {
        super.onFinishInflate()
        mLifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //预览时会报错
//        attributes?.let {
//            attributeHelper = MultiFunctionAttributeHelper(context, attributes)
//        }
        mLifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        attributeHelper.recycle()
        setOnCheckedChangeListener(null)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    init {
        attributes?.let {
            attributeHelper = MultiFunctionAttributeHelper(context, attributes)
        }
        initView()
        loadViewAttribute()
    }

    private lateinit var checkBox: CheckBox
    private lateinit var leftImage: ImageView
    private lateinit var title: TextView
    private lateinit var subTitle: TextView
    private lateinit var rightImage: ImageView

    private fun initView() {
        View.inflate(context, R.layout.multi_function_layout, this)
        checkBox = findViewById(R.id.checkbox)
        leftImage = findViewById(R.id.leftImage)
        title = findViewById(R.id.title)
        subTitle = findViewById(R.id.subTitle)
        rightImage = findViewById(R.id.rightImage)
        setClick()
    }

    private fun setClick() {
        if (attributeHelper.clickOnlyCheckBox == true) {
            setOnClickListener {
                getCheckBox().performClick()
                (parent as ViewGroup).performClick()
            }
        }
    }

    private fun loadViewAttribute() {
        loadCheckBoxAttribute()
        loadLeftImageAttribute()
        loadTitleAttribute()
        loadSubTitleAttribute()
        loadRightImageAttribute()
    }

    private fun loadCheckBoxAttribute() {
        showCheckBox(attributeHelper.showCheckBox ?: false)
        attributeHelper.checkboxBackground?.let {
            setCheckBoxBackground(it)
        }
        attributeHelper.checkboxBackgroundColor?.let {
            setCheckBoxBackgroundColor(it)
        }
        attributeHelper.checkBoxWidth?.let {
            setCheckBoxWidth(it)
        }
        attributeHelper.isCheck?.let {
            setCheckBoxChecked(it)
        }
        attributeHelper.checkboxLeftMarge?.let {
            setCheckBoxMarginLeft(it)
        }
        attributeHelper.checkboxRightMarge?.let {
            setCheckBoxMarginRight(it)
        }
    }

    private fun loadLeftImageAttribute() {
        attributeHelper.showLeftImage?.let {
            showLeftImage(it)
        }
        attributeHelper.leftImageBackground?.let {
            setLeftImageBackground(it)
        }
        attributeHelper.leftImageSrc?.let {
            setLeftImageSrc(it)
        }
        setLeftImageScaleType(ScaleTypeHelper.toAndroidScaleType(attributeHelper.leftImageScaleType))
        attributeHelper.leftImageWidth?.let {
            setLeftImageWidth(it)
        }
        attributeHelper.leftImageLeftMarge?.let {
            setLeftImageMarginLeft(it)
        }
        attributeHelper.leftImageRightMarge?.let {
            setLeftImageMarginRight(it)
        }
    }

    private fun loadTitleAttribute() {
        setTitle(attributeHelper.title ?: "")
        attributeHelper.titleHint?.let {
            setTitleHint(it)
        }
        attributeHelper.titleBackground?.let {
            setTitleBackground(it)
        }
        attributeHelper.titleColor?.let {
            setTitleColor(it)
        }
        attributeHelper.titleHintColor?.let {
            setTitleHintColor(it)
        }
        attributeHelper.titleSize?.let {
            setTitleSize(it.toFloat())
        }
        attributeHelper.titleMaxLine?.let {
            setTitleMaxLine(it)
        }
        attributeHelper.titleWpfStyle?.let {
            setTitleStyle(TypefaceHelper.getAndroidTypeface(it))
        }
    }

    private fun loadSubTitleAttribute() {
        setSubTitle(attributeHelper.subTitle ?: "")
        attributeHelper.subTitleHint?.let {
            setSubTitleHint(it)
        }
        attributeHelper.subTitleBackground?.let {
            setSubTitleBackground(it)
        }
        attributeHelper.subTitleColor?.let {
            setSubTitleColor(it)
        }
        attributeHelper.subTitleHintColor?.let {
            setSubTitleHintColor(it)
        }
        attributeHelper.subTitleSize?.let {
            setSubTitleSize(it.toFloat())
        }
        attributeHelper.subTitleMaxLine?.let {
            setSubTitleMaxLine(it)
        }
        attributeHelper.subTitleWpfStyle?.let {
            setSubTitleStyle(TypefaceHelper.getAndroidTypeface(it))
        }
        attributeHelper.subTitleMarginTop?.let {
            setSubTitleMarginTop(it)
        }
    }

    private fun loadRightImageAttribute() {
        attributeHelper.showRightImage?.let {
            showRightImage(it)
        }
        attributeHelper.rightImageBackground?.let {
            setRightImageBackground(it)
        }
        attributeHelper.rightImageSrc?.let {
            setRightImageSrc(it)
        }
        setRightImageScaleType(ScaleTypeHelper.toAndroidScaleType(attributeHelper.rightImageScaleType))
        attributeHelper.rightImageWidth?.let {
            setRightImageWidth(it)
        }
        attributeHelper.rightImageLeftMarge?.let {
            setRightImageMarginLeft(it)
        }
        attributeHelper.rightImageRightMarge?.let {
            setRightImageMarginRight(it)
        }
    }

    fun getCheckBox(): CheckBox {
        return checkBox
    }

    fun getLeftImage(): ImageView {
        return leftImage
    }

    fun getTitle(): TextView {
        return title
    }

    fun getSubTitle(): TextView {
        return subTitle
    }

    fun getRightImage(): ImageView {
        return rightImage
    }

    fun showCheckBox(show: Boolean) {
        checkBox.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setCheckBoxWidth(width: Int) {
        checkBox.layoutParams?.let {
            it.width = width
            it.height = width
            checkBox.requestLayout()
        } ?: let {
            checkBox.layoutParams = LayoutParams(width, width)
        }
    }

    fun setCheckBoxBackground(@DrawableRes res: Int) {
        checkBox.setBackgroundResource(res)
    }

    fun setCheckBoxBackgroundColor(color: Color) {
        checkBox.background = color.toDrawable()
    }

    fun setCheckBoxChecked(checked: Boolean) {
        checkBox.isChecked = checked
    }

    fun setCheckBoxMarginLeft(margin: Int) {
        (checkBox.layoutParams as? MarginLayoutParams)?.leftMargin = margin
    }

    fun setCheckBoxMarginRight(margin: Int) {
        (checkBox.layoutParams as? MarginLayoutParams)?.rightMargin = margin
    }

    fun showLeftImage(show: Boolean) {
        leftImage.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setLeftImageBackground(@DrawableRes res: Int) {
        leftImage.setBackgroundResource(res)
    }

    fun setLeftImageWidth(width: Int) {
        leftImage.layoutParams?.let {
            it.width = width
            it.height = width
            leftImage.requestLayout()
        } ?: let {
            leftImage.layoutParams = LayoutParams(width, width)
        }
    }

    fun setLeftImageSrc(@DrawableRes src: Int) {
        leftImage.setImageResource(src)
    }

    fun setLeftImageScaleType(scaleType: ImageView.ScaleType) {
        leftImage.scaleType = scaleType
    }

    fun setLeftImageMarginLeft(margin: Int) {
        (leftImage.layoutParams as? MarginLayoutParams)?.leftMargin = margin
    }

    fun setLeftImageMarginRight(margin: Int) {
        (leftImage.layoutParams as? MarginLayoutParams)?.rightMargin = margin
    }

    fun showTitle(show: Boolean) {
        title.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setTitleBackground(@DrawableRes res: Int) {
        title.setBackgroundResource(res)
    }

    fun setTitle(titleStr: CharSequence) {
        title.text = titleStr
        showTitle(titleStr.isNotEmpty())
    }

    fun setTitleHint(titleHintStr: CharSequence) {
        title.hint = titleHintStr
        showTitle(titleHintStr.isNotEmpty())
    }

    fun setTitleColor(color: Int) {
        title.setTextColor(color)
    }

    fun setTitleHintColor(color: Int) {
        title.setHintTextColor(color)
    }

    fun setTitleSize(size: Float) {
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    fun setTitleMaxLine(max: Int) {
        title.maxLines = max
        title.ellipsize = TextUtils.TruncateAt.END
    }

    fun setTitleStyle(style: Typeface) {
        title.typeface = style
    }

    fun showSubTitle(show: Boolean) {
        subTitle.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setSubTitleBackground(@DrawableRes res: Int) {
        subTitle.setBackgroundResource(res)
    }

    fun setSubTitle(subTitleStr: CharSequence) {
        subTitle.text = subTitleStr
        showSubTitle(subTitleStr.isNotEmpty())
    }

    fun setSubTitleHint(subTitleHintStr: CharSequence) {
        subTitle.hint = subTitleHintStr
        showSubTitle(subTitleHintStr.isNotEmpty())
    }

    fun setSubTitleColor(color: Int) {
        subTitle.setTextColor(color)
    }

    fun setSubTitleHintColor(color: Int) {
        subTitle.setHintTextColor(color)
    }

    fun setSubTitleSize(size: Float) {
        subTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    fun setSubTitleMaxLine(max: Int) {
        subTitle.maxLines = max
        subTitle.ellipsize = TextUtils.TruncateAt.END
    }

    fun setSubTitleStyle(style: Typeface) {
        subTitle.typeface = style
    }

    fun setSubTitleMarginTop(margin: Int) {
        (subTitle.layoutParams as? MarginLayoutParams)?.topMargin = margin
    }

    fun showRightImage(show: Boolean) {
        rightImage.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setRightImageBackground(@DrawableRes res: Int) {
        rightImage.setBackgroundResource(res)
    }

    fun setRightImageWidth(width: Int) {
        rightImage.layoutParams?.let {
            it.width = width
            it.height = width
            rightImage.requestLayout()
        } ?: let {
            rightImage.layoutParams = LayoutParams(width, width)
        }
    }

    fun setRightImageSrc(@DrawableRes src: Int) {
        rightImage.setImageResource(src)
    }

    fun setRightImageScaleType(scaleType: ImageView.ScaleType) {
        rightImage.scaleType = scaleType
    }

    fun setRightImageMarginLeft(margin: Int) {
        (rightImage.layoutParams as? MarginLayoutParams)?.leftMargin = margin
    }

    fun setRightImageMarginRight(margin: Int) {
        (rightImage.layoutParams as? MarginLayoutParams)?.rightMargin = margin
    }

    override fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        checkBox.setOnCheckedChangeListener(listener)
    }

    override fun setChecked(checked: Boolean) {
        checkBox.isChecked = checked
    }

    override fun isChecked(): Boolean {
        return checkBox.isChecked
    }

    override fun toggle() {
        checkBox.toggle()
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
}

interface CheckView : Checkable {

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?)
}
