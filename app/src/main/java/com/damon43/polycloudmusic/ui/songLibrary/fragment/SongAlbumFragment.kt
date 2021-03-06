package com.damon43.polycloudmusic.ui.songLibrary.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.damon43.common.base.BaseFragment
import com.damon43.common.commonutils.LogUtils
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.ui.songLibrary.adapter.SongAlbumAdapter
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongAlbumContract
import com.damon43.polycloudmusic.ui.songLibrary.model.SongAlbumModel
import com.damon43.polycloudmusic.ui.songLibrary.presenter.SongAlbumPresenter
import org.jetbrains.anko.find

/**
 * Created by lenovo on 2017/9/16.
 */
class SongAlbumFragment : BaseFragment(), SongAlbumContract.View {

    var albumAdapter: SongAlbumAdapter? = null
    lateinit var rvSongAlbums: RecyclerView
    lateinit var mPresenter: SongAlbumPresenter
    override fun getLayoutResource(): Int = R.layout.fragment_kind_album

    override fun initView() {
        rvSongAlbums = contentView.find(R.id.rvSongAlbum)
        rvSongAlbums.layoutManager = GridLayoutManager(mContext, 2)

        mPresenter = SongAlbumPresenter()
        mPresenter.setVM(SongAlbumModel(), SongAlbumFragment@ this)
        checkPermission()

    }

    override fun initPresenter() {
    }

    override fun showAllCustomAlbums(songs: List<Album>) {
        if (albumAdapter == null) {
            albumAdapter = SongAlbumAdapter(mContext, songs)
            rvSongAlbums.adapter = albumAdapter
        }
    }

    private val PERMISSIONS_READ_EXTERNAL_STORAGE: Int = 100

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_READ_EXTERNAL_STORAGE)
            LogUtils.logD("check")

        } else {
            mPresenter.loadAllCustomAlbums(mContext)
            LogUtils.logD("load")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_READ_EXTERNAL_STORAGE -> {
                if (grantResults.size > 0) {
                    mPresenter.loadAllCustomAlbums(mContext)
                    LogUtils.logD("success")
                } else {
                    //failed...
                    LogUtils.logD("failed")
                }
            }
        }
    }
}