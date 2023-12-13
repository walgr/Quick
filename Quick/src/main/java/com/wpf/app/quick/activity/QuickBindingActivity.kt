package com.wpf.app.quick.activity

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.wpf.app.quick.activity.viewmodel.QuickVBModel

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickBindingActivity<VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    titleName: String = ""
) : QuickVBActivity<QuickVBModel<VB>, VB>(layoutId, titleName = titleName)