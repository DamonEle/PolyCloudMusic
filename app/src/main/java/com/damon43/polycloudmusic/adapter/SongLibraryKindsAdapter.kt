package com.damon43.polycloudmusic.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.damon43.common.base.BaseFragmentAdapter

/**
 * Created by lenovo on 2017/9/16.
 */
class SongLibraryKindsAdapter : BaseFragmentAdapter {
    constructor(fm: FragmentManager, list: List<Fragment>) : super(fm, list)
    constructor(fm: FragmentManager, list: List<Fragment>, titls: List<String>) : super(fm, list, titls)
}