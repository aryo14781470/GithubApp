package com.dicoding.project.githubuser.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.project.githubuser.R
import com.dicoding.project.githubuser.ui.followers.FollowersFragment
import com.dicoding.project.githubuser.ui.following.FollowingFragment

class SectionPagerAdapter (private val mCtx: Context, fm: FragmentManager, data: Bundle): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragmnent: Fragment? = null
        when(position){
            0 -> fragmnent = FollowersFragment()
            1 -> fragmnent = FollowingFragment()
        }
        fragmnent?.arguments = this.fragmentBundle
        return fragmnent as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLES[position])
    }

}