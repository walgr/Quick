package com.wpf.app.quick.helper.binddatahelper

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wpf.app.quick.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class Url2ImageView : BindD2VHelper<ImageView, String> {

    override fun initView(view: ImageView, url: String) {
        if (view.context == null) return
        val activity = view.context as Activity
        if (activity.isDestroyed || activity.isFinishing) return
        Glide.with(activity)
            .load(url)
            .into(view)
    }
}