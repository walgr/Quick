package com.wpf.app.quickrecyclerview.listeners

import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
interface RequestAndItemCallback<Request : RequestData, T>: RequestDataAndCallback<Request, QuickItemData>