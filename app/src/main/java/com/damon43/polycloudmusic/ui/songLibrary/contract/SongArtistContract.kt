package com.damon43.polycloudmusic.ui.songLibrary.contract

import android.content.Context
import com.damon43.common.base.BaseModel
import com.damon43.common.base.BasePresenter
import com.damon43.common.base.BaseView
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Artist
import rx.Observable

/**
 * desc 歌曲list 契约类
 * Created by lenovo on 2017/10/11.
 */
abstract class SongArtistContract {
    interface Model : BaseModel {
        /*获取本地全部音乐*/
        fun loadAllCustomArtists(context: Context): Observable<List<Artist>>
    }

    interface View : BaseView {
        fun showAllCustomArtists(songs: List<Artist>)
    }

    abstract class Presenter : BasePresenter<Model, View>() {
        abstract fun loadAllCustomArtists(context: Context)
    }
}