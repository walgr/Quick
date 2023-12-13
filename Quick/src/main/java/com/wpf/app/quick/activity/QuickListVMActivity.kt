package com.wpf.app.quick.activity

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData

open class QuickListVMActivity<VM : QuickViewModel<out QuickView>> @JvmOverloads constructor(
    dataList: List<QuickItemData>? = null,
    dataLoader: (RequestData.() -> List<QuickItemData>?)? = null,
    @LayoutRes layoutId: Int = 0,
    @IdRes listId: Int = 0,
    titleName: String = ""
): QuickFragmentActivity(QuickListVMFragment<VM>(dataList, dataLoader, layoutId, listId, titleName = titleName))