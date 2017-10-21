package com.damon43.polycloudmusic.ui.songLibrary.model

import android.content.Context
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Artist
import com.damon43.polycloudmusic.helper.untls.AlbumLoader
import com.damon43.polycloudmusic.helper.untls.ArtistLoader
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongArtistContract
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * desc
 * Created by lenovo on 2017/10/11.
 */
class SongArtistModel : SongArtistContract.Model {
    override fun onFailed(error: String?) {

    }

    override fun loadAllCustomArtists(context: Context): Observable<List<Artist>> {
        return Observable.just(ArtistLoader.getAllArtist(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}