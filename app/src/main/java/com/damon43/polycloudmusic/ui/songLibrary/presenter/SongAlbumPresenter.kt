package com.damon43.polycloudmusic.ui.songLibrary.presenter

import android.content.Context
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongAlbumContract
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongListContract
import rx.Subscriber

/**
 * desc 歌曲列表fragment界面
 * Created by lenovo on 2017/10/12.
 */
class SongAlbumPresenter : SongAlbumContract.Presenter() {

    override fun loadAllCustomAlbums(context: Context) {
        mModel.loadAllCustomAlbums(context)
                .subscribe(object : Subscriber<List<Album>>() {
                    override fun onError(e: Throwable?) {
                        mView.onLoadFailed(e, "")
                    }

                    override fun onCompleted() {
                        mView.onLoaded()
                    }

                    override fun onNext(t: List<Album>) {
                        mView.showAllCustomAlbums(t)
                    }

                })
    }

}