package com.damon43.polycloudmusic.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.damon43.common.baserx.RxBus
import com.damon43.polycloudmusic.base.Constant
import com.damon43.polycloudmusic.event.MusicEvent
import com.damon43.polycloudmusic.event.MusicStartEvent

/**
 * desc 用来接收远程server发送的广播 并通过事件订阅分发给订阅者
 * Created by lenovo on 2017/12/22.
 */
class RemoteServerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var event: MusicEvent
        when (intent?.action) {
            Constant.ACTION_MUSIC_START -> {
                event = MusicStartEvent(Constant.ACTION_MUSIC_START)
                RxBus.getInstance().post(intent.action,event)
            }
        }

    }
}