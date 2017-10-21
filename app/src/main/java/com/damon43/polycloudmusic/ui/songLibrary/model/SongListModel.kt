package com.damon43.polycloudmusic.ui.songLibrary.model

import android.content.Context
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.helper.untls.SongLoader
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongListContract
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * desc
 * Created by lenovo on 2017/10/11.
 */
class SongListModel : SongListContract.Model {
    override fun onFailed(error: String?) {

    }

    override fun loadAllCustomSongs(context: Context): Observable<List<Song>> {
        return Observable.from(SongLoader.getAllSongs(context))
                .filter { it.duration > 1000 * 60 }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}