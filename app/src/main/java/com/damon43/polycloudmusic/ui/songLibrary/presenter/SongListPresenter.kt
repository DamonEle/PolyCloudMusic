package com.damon43.polycloudmusic.ui.songLibrary.presenter

import android.content.Context
import com.damon43.polycloudmusic.base.Constant
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.event.MusicEvent
import com.damon43.polycloudmusic.event.MusicStartEvent
import com.damon43.polycloudmusic.ui.songLibrary.contract.SongListContract
import rx.Subscriber
import rx.functions.Action1
import rx.functions.Func0

/**
 * desc 歌曲列表fragment界面
 * Created by lenovo on 2017/10/12.
 */
class SongListPresenter(context: Context) : SongListContract.Presenter() {

    init {
        rxManager.on(Constant.ACTION_MUSIC_STATE,object :  Action1<MusicEvent> {
            override fun call(t: MusicEvent?) {
                when(t){
                     is MusicStartEvent -> musicStart(t)
                }
            }
        })
    }

    private fun musicStart() {

    }

    override fun loadAllCustomSongs(context: Context) {
        mModel.loadAllCustomSongs(context)
                .subscribe(object : Subscriber<List<Song>>() {
                    override fun onError(e: Throwable?) {
                        mView.onLoadFailed(e, "")
                    }

                    override fun onCompleted() {
                        mView.onLoaded()
                    }

                    override fun onNext(t: List<Song>) {
                        mView.showAllCustomSongs(t)
                    }

                })
    }

}