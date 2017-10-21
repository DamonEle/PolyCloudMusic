package com.damon43.polycloudmusic.ui.songLibrary.presenter

import android.content.Context
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Artist
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongAlbumContract
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongArtistContract
import rx.Subscriber

/**
 * desc 歌曲列表fragment界面
 * Created by lenovo on 2017/10/12.
 */
class SongArtistPresenter : SongArtistContract.Presenter() {

    override fun loadAllCustomArtists(context: Context) {
        mModel?.loadAllCustomArtists(context)
                ?.subscribe(object : Subscriber<List<Artist>>() {
                    override fun onError(e: Throwable?) {
                        mView.onLoadFailed(e, "")
                    }

                    override fun onCompleted() {
                        mView.onLoaded()
                    }

                    override fun onNext(t: List<Artist>) {
                        mView.showAllCustomArtists(t)
                    }

                })
    }

}