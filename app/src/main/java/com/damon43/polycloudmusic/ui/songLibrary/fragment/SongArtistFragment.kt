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
import com.damon43.polycloudmusic.bean.Artist
import com.damon43.polycloudmusic.ui.songLibrary.adapter.SongArtistAdapter
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongArtistContract
import com.damon43.polycloudmusic.ui.songLibrary.model.SongArtistModel
import com.damon43.polycloudmusic.ui.songLibrary.presenter.SongArtistPresenter
import org.jetbrains.anko.find

/**
 * Created by lenovo on 2017/9/16.
 */
class SongArtistFragment : BaseFragment(), SongArtistContract.View {

    lateinit var mPresenter: SongArtistPresenter
    lateinit var mArtistRv: RecyclerView
    var mArtistAdapter: SongArtistAdapter? = null

    override fun getLayoutResource(): Int = R.layout.fragment_kind_singer

    override fun initView() {
        mArtistRv = contentView.find(R.id.rvSongArtist)
        mArtistRv.layoutManager = GridLayoutManager(mContext, 2)

        checkPermission()
    }

    override fun initPresenter() {
        mPresenter = SongArtistPresenter()
        mPresenter.setVM(SongArtistModel(), SongArtistFragment@ this)
    }

    override fun showAllCustomArtists(artists: List<Artist>) {
        if (mArtistAdapter == null) {
            mArtistAdapter = SongArtistAdapter(mContext, artists)
            mArtistRv.adapter = mArtistAdapter
        }
    }

    private val PERMISSIONS_READ_EXTERNAL_STORAGE: Int = 100

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_READ_EXTERNAL_STORAGE)
        } else {
            mPresenter.loadAllCustomArtists(mContext)
            LogUtils.logD("load")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_READ_EXTERNAL_STORAGE -> {
                if (grantResults.size > 0) {
                    mPresenter.loadAllCustomArtists(mContext)
                } else {
                    //failed...
                }
            }
        }
    }
}