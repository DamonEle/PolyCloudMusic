package com.damon43.polycloudmusic.ui.songLibrary.model

import android.content.Context
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.helper.untls.AlbumLoader
import com.damon43.polycloudmusic.helper.untls.SongLoader
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongAlbumContract
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongListContract
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * desc
 * Created by lenovo on 2017/10/11.
 */
class SongAlbumModel : SongAlbumContract.Model {
    override fun onFailed(error: String?) {

    }

    override fun loadAllCustomAlbums(context: Context): Observable<List<Album>> {
        return Observable.just(AlbumLoader.getAllAlbum(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}