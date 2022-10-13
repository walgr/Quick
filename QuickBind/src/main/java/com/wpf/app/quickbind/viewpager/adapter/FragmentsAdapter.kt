package com.wpf.app.quickbind.viewpager.adapter

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wpf.app.quickbind.interfaces.BindBaseFragment

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
class FragmentsAdapter(
    fm: FragmentManager, private val fragments: List<out BindBaseFragment>
) : FragmentPagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {
        return fragments[i] as Fragment
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(@NonNull `object`: Any): Int {
        return POSITION_NONE
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].getTitle()
    }
}