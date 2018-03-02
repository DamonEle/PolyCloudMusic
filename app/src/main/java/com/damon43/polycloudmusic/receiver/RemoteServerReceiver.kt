package com.damon43.polycloudmusic.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.damon43.common.baserx.RxBus
import com.damon43.polycloudmusic.base.Constant
import com.damon43.polycloudmusic.event.MusicEvent
import com.damon43.polycloudmusic.event.MusicPauseEvent
import com.damon43.polycloudmusic.event.MusicRestartEvent
import com.damon43.polycloudmusic.event.MusicStartEvent

/**
 * desc 用来接收远程server发送的广播 并通过事件订阅分发给订阅者
 * Created by lenovo on 2017/12/22.
 */
class RemoteServerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var event: MusicEvent = MusicEvent("",0)
        val bundle = intent?.getBundleExtra(Constant.BUNDLE_KEY_SONG_INFO)
        val id = bundle?.getLong(Constant.
                BUNDLE_KEY_SONG_ID) ?: 0
        when (intent?.action) {
            Constant.ACTION_MUSIC_START -> {
                event = MusicStartEvent(Constant.ACTION_MUSIC_START, id, bundle?.getString(Constant.BUNDLE_KEY_SONG_NAME) ?: "null"
                        , bundle?.getString(Constant.BUNDLE_KEY_SONG_AUTHOR) ?: "null"
                        , bundle?.getString(Constant.BUNDLE_KEY_SONG_IMG_URL) ?: "")

            }
            Constant.ACTION_MUSIC_PAUSE -> event = MusicPauseEvent(Constant.ACTION_MUSIC_PAUSE, id)
            Constant.ACTION_MUSIC_RESTART -> event = MusicRestartEvent(Constant.ACTION_MUSIC_RESTART, id)
        }
        RxBus.getInstance().post(Constant.ACTION_MUSIC_STATE, event)
    }
}