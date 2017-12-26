package com.damon43.polycloudmusic.ui.songLibrary.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.damon43.common.base.BaseFragment
import com.damon43.common.commonutils.LogUtils
import com.damon43.common.commonutils.ToastUtils
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.ui.songLibrary.adapter.SongListAdapter
import com.damon43.polycloudmusic.base.Constant
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.event.MusicEvent
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


    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun getLayoutResource(): Int = R.layout.fragment_kind_song

    override fun initView() {
        rvSongList = contentView.find(R.id.rvSongList)
        rvSongList.layoutManager = LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false)
        rvSongList.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
    }

    override fun initPresenter() {
        mPresenter = SongListPresenter(mContext)
        mPresenter.setVM(SongListModel(), SongListFragment@ this)
        checkPermission()
    }

    override fun showAllCustomSongs(songs: List<Song>) {
        if (songAdapter == null) {
            songAdapter = SongListAdapter(mContext, songs)
            rvSongList.adapter = songAdapter
        }
    }

    override fun showMusicStart(event: MusicEvent?) {
        songAdapter?.notifyDataSetChanged()
    }

    private val PERMISSIONS_READ_EXTERNAL_STORAGE: Int = 100

    fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    permissions, PERMISSIONS_READ_EXTERNAL_STORAGE)
            ToastUtils.show("申请权限", 1000)
        } else {
            mPresenter.loadAllCustomSongs(mContext)
            ToastUtils.show("权限已经申请", 1000)
            LogUtils.logD("load")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_READ_EXTERNAL_STORAGE -> {
                if (grantResults.size > 0) {
                    mPresenter.loadAllCustomSongs(mContext)
                    ToastUtils.show("权限已经同意", 1000)

                } else {
                    //failed...
                    ToastUtils.show("权限申请被拒绝", 1000)

                }
            }
        }
    }


}