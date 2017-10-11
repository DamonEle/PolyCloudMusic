package com.damon43.polycloudmusic.ui.songLibrary.fragment

import com.damon43.common.base.BaseFragment
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.adapter.SongListAdapter


class SongListFragment : BaseFragment() {

    lateinit var songAdapter : SongListAdapter

    override fun getLayoutResource(): Int = R.layout.fragment_kind_song

    override fun initView() {
    }

    override fun initPresenter() {
    }

}