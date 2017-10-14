package com.damon43.polycloudmusic.ui.songLibrary.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.damon43.common.base.BaseFragment
import com.damon43.common.commonutils.LogUtils
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.adapter.SongListAdapter
import com.damon43.polycloudmusic.base.Constant
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongListContract
import com.damon43.polycloudmusic.ui.songLibrary.model.SongListModel
import com.damon43.polycloudmusic.ui.songLibrary.presenter.SongListPresenter
import com.zhy.m.permission.MPermissions
import com.zhy.m.permission.PermissionDenied
import com.zhy.m.permission.PermissionGrant
import org.jetbrains.anko.find


class SongListFragment : BaseFragment(), SongListContract.View {

    lateinit var mPresenter: SongListPresenter
    lateinit var rvSongList: RecyclerView
    var songAdapter: SongListAdapter? = null

    override fun getLayoutResource(): Int = R.layout.fragment_kind_song

    override fun initView() {
        rvSongList = contentView.find(R.id.rvSongList)
        rvSongList.layoutManager = LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false)
        rvSongList.addItemDecoration(DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL))
    }

    override fun initPresenter() {
        mPresenter = SongListPresenter()
        mPresenter.setVM(SongListModel(), SongListFragment@ this)
        mPresenter.loadAllCustomSongs(mContext)

    }

    override fun showAllCustomSongs(songs: List<Song>) {
        if (songAdapter == null) {
            songAdapter = SongListAdapter(mContext, songs)
            rvSongList.adapter = songAdapter
        }
    }

    @PermissionGrant(Constant.RUQUEST_LOAD_MUSIC)
    fun requestLoadMusicSuccess() {
        LogUtils.logD("success")
        mPresenter.loadAllCustomSongs(mContext)
    }

    @PermissionDenied(Constant.RUQUEST_LOAD_MUSIC)
    fun requestLoadMusicFailed() {
        LogUtils.logD("failed ")
    }

    fun requestLoadMusic() {
        MPermissions.requestPermissions(SongListFragment@ this, Constant.RUQUEST_LOAD_MUSIC,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}