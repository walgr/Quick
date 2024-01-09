package com.wpf.app.quick.activity

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.wpf.app.quick.activity.viewmodel.QuickVBModel

/**
 * Created by 王朋飞 on 2024/01/09
 *
 */
abstract class QuickBindingFragment<VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    titleName: String = ""
) : QuickVBFragment<QuickVBModel<VB>, VB>(layoutId, titleName = titleName)