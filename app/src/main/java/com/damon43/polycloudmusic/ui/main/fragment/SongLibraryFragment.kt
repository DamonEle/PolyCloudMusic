package com.damon43.polycloudmusic.ui.main.fragment

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.damon43.common.base.BaseFragment
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.adapter.SongLibraryKindsAdapter
import com.damon43.polycloudmusic.ui.songLibrary.fragment.SongAlbumFragment
import com.damon43.polycloudmusic.ui.songLibrary.fragment.SongListFragment
import com.damon43.polycloudmusic.ui.songLibrary.fragment.SongSingerFragment
import org.jetbrains.anko.find

/**
 * 歌曲列表
 */
class SongLibraryFragment : BaseFragment() {

    var kindsAdapter: SongLibraryKindsAdapter? = null
    val kindFragment = SongListFragment()
    val albumFragment = SongAlbumFragment()
    val singerFragment = SongSingerFragment()
    val fragmengs: List<Fragment> = listOf(kindFragment, albumFragment, singerFragment)
    var titles: List<String> = listOf()
    var tlTop:TabLayout? = null
    var vpKinds:ViewPager? = null

    override fun initView() {
        titles = listOf(mContext.getString(R.string.kind_song), mContext.getString
        (R.string.kind_album), mContext.getString(R.string.kind_singer))
        tlTop = contentView.find(R.id.tlTop)
        vpKinds = contentView.find(R.id.vpKinds)
            tlTop!!.addTab(tlTop!!.newTab().setText(R.string.kind_song))
            tlTop!!.addTab(tlTop!!.newTab().setText(R.string.kind_album))
            tlTop!!.addTab(tlTop!!.newTab().setText(R.string.kind_singer))
            tlTop!!.setupWithViewPager(vpKinds)
        kindsAdapter = SongLibraryKindsAdapter(childFragmentManager, fragmengs, titles)
        vpKinds!!.adapter = kindsAdapter
    }

    override fun initPresenter() {
    }

    override fun getLayoutResource(): Int = R.layout.fragment_song_library
}