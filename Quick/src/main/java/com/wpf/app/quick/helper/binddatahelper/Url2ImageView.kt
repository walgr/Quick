package com.wpf.app.quick.helper.binddatahelper

import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object Url2ImageView : BindD2VHHelper<ImageView, String> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: ImageView, data: String) {
        if (view.context == null || data.isEmpty()) return
        val activity = view.context as FragmentActivity
        if (activity.isDestroyed || activity.isFinishing) return
        Glide.with(activity)
            .load(data)
            .into(view)
    }
}