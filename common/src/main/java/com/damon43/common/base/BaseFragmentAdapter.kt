package com.damon43.common.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.damon43.common.commonutils.CollectionUtils

import java.util.ArrayList

/**
 * @author damonmasty
 * *         Created on 上午10:39 17-1-29.
 */

class BaseFragmentAdapter : FragmentPagerAdapter {

    internal var fragmentList: List<Fragment> = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) : super(fm) {
        this.fragmentList = fragmentList
        this.mTitles = mTitles
    }

    override fun getPageTitle(position: Int): CharSequence {
        return  mTitles?.get(position).toString()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}
