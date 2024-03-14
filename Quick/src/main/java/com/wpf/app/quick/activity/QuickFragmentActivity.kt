package com.wpf.app.quick.activity

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wpf.app.quick.R
import com.wpf.app.quickutil.bind.runOnContext

open class QuickFragmentActivity(
    open val fragment: Fragment
): QuickActivity(
    layoutViewInContext = runOnContext {
        RelativeLayout(it).apply {
            id = R.id.quickRoot
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }
) {
    override fun initView(view: View) {
        supportFragmentManager.commit {
            add(R.id.quickRoot, fragment)
        }
    }
}