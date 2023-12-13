package com.wpf.app.quick.activity

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData

open class QuickListVBActivity<VM : QuickVBModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    dataList: List<QuickItemData>? = null,
    dataLoader: (RequestData.() -> List<QuickItemData>?)? = null,
    @LayoutRes layoutId: Int = 0,
    @IdRes listId: Int = 0,
    titleName: String = ""
) : QuickFragmentActivity(QuickListVBFragment<VM, VB>(dataList, dataLoader, layoutId, listId, titleName = titleName))