package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import com.wpf.app.quickbind.annotations.BindD2VHelper

object Request2View: BindD2VHelper<View, RequestAndCallbackWithView<out QuickItemData, out View>> {
}