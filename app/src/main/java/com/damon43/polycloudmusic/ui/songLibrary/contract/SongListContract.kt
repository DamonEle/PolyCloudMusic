package com.damon43.polycloudmusic.ui.songLibrary.contract

import android.content.Context
import com.damon43.common.base.BaseModel
import com.damon43.common.base.BasePresenter
import com.damon43.common.base.BaseView
import com.damon43.polycloudmusic.bean.Song
import rx.Observable

/**
 * desc 歌曲list 契约类
 * Created by lenovo on 2017/10/11.
 */
abstract class SongListContract {
    interface Model : BaseModel {
        /*获取本地全部音乐*/
        fun loadAllCustomSongs(context: Context): Observable<Song>
    }

    interface View : BaseView {
        fun showAllCustomSongs(songs: Observable<Song>)
    }

    abstract class Presenter : BasePresenter<Model, View>() {
        abstract fun loadAllCustomSongs(context: Context)
    }
}