package cn.goodjobs.client.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import cn.goodjobs.client.R
import cn.goodjobs.client.helper.MultiFunctionAttributeHelper

/**
 * Created by 王朋飞 on 2022/5/7.
 * 多功能
 *                          Title
 * CheckBox   LeftImageview            RightImage
 *                          SubTitle
 */
open class MultiFunctionView
@JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr) {

    private lateinit var attributeHelper: MultiFunctionAttributeHelper

    init {
        attributes?.let {
            attributeHelper = MultiFunctionAttributeHelper(context, attributes)
        }
        View.inflate(context, R.layout.multi_function_layout, this)
        initView()
        loadViewAttribute()
    }

    private lateinit var checkBox: CheckBox
    private lateinit var leftImage: ImageView
    private lateinit var title: TextView
    private lateinit var subTitle: TextView
    private lateinit var rightImage: ImageView

    private fun initView() {
        checkBox = findViewById(R.id.checkbox)
        leftImage = findViewById(R.id.leftImage)
        title = findViewById(R.id.title)
        subTitle = findViewById(R.id.subTitle)
        rightImage = findViewById(R.id.rightImage)
    }

    private fun loadViewAttribute() {
        loadCheckBoxAttribute()
        loadLeftImageAttribute()
    }

    private fun loadCheckBoxAttribute() {
        attributeHelper.showCheckBox?.let {
            showCheckBox(it)
        }
        attributeHelper.checkboxBackground?.let {
            setCheckBoxBackground(it)
        }
        attributeHelper.checkBoxWidth?.let {
            setCheckBoxWidth(it)
        }
        attributeHelper.isChecked?.let {
            setCheckBoxChecked(it)
        }
    }

    private fun loadLeftImageAttribute() {
        attributeHelper.showLeftImage?.let {
            showLeftImage(it)
        }
        attributeHelper.leftImageSrc?.let {
            setLeftImageSrc(it)
        }
    }

    private fun loadTitleAttribute() {
        attributeHelper.
    }

    private fun loadSubTitleAttribute() {

    }

    fun getCheckBox(): CheckBox {
        return checkBox
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

    fun setCheckBoxChecked(checked: Boolean) {
        checkBox.isChecked = checked
    }

    fun showLeftImage(show: Boolean) {
        leftImage.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setLeftImageSrc(@DrawableRes src: Int) {
        leftImage.setImageResource(src)
    }

    fun showTitle(show: Boolean) {
        title.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setTitle(titleStr: String) {
        title.text = titleStr
        showTitle(titleStr.isNotEmpty())
    }

    fun showSubTitle(show: Boolean) {
        subTitle.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setSubTitle(subTitleStr: String) {
        subTitle.text = subTitleStr
        showTitle(subTitleStr.isNotEmpty())
    }

    fun setSubTitleMarginTop(topMargin: Int) {
        (subTitle.layoutParams as? MarginLayoutParams)?.let {
            it.topMargin = topMargin
        }
    }
}