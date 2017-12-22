package com.damon43.polycloudmusic.helper

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import com.damon43.common.commonutils.LogUtils
import com.damon43.polycloudmusic.bean.MusicPlayTrack

/**
 * desc 管理音乐播放的各种相关操作
 * Created by lenovo on 2017/10/20.
 */
class MusicPlayManager(var mContext: Context) : MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener
        , MediaPlayer.OnCompletionListener {

    val ACTION_MUSIC_STARTED = "ACTION_MUSIC_STARTED"

    private var mCurrentMediaPlayer = MediaPlayer()

    private var mNextMediaPlayer: MediaPlayer? = null
    /*需要提取的数据*/
    val PROJECTION = arrayOf("audio._id AS _id", MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID)


    var mMusicCursor: Cursor? = null
    var currentPos = 0
    var mMusicList = mutableListOf<MusicPlayTrack>()

    val isPlaying = true
    private val IDCOLIDX = 0
    /**
     * 按队列播放音乐
     * 1.假如没有播放中 正常播放
     * 2.      有播放中  a.如果与播放的歌曲id一致，重新播放 b.不一致，初始化播放器设置并播放
     */
    fun playAll(list: LongArray?, position: Int) {

        if (currentPos != position) {
            currentPos = position
            val id = list?.get(position) ?: 0//获取专辑id
            LogUtils.logD("播放id:" + id)
            //获取携带音乐全部信息的游标
            mMusicCursor = openCursorAndGoToFirst(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    PROJECTION, "_id=" + id, null)
            val url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() + "/" + ((mMusicCursor?.getLong(IDCOLIDX) ?: 1).toString())
            mMusicList.add(MusicPlayTrack(id, -1, -1, -1, url))
            LogUtils.logD("播放:" + url)

            mCurrentMediaPlayer.setDataSource(url)
            mCurrentMediaPlayer.setOnPreparedListener(MusicPlayManager@ this)
            mCurrentMediaPlayer.setOnErrorListener(MusicPlayManager@ this)
            mCurrentMediaPlayer.prepareAsync()
        } else {
            if (mCurrentMediaPlayer.isPlaying){
                mCurrentMediaPlayer.pause()
            } else {
                mCurrentMediaPlayer.start()
            }
        }
    }

    private fun openCursorAndGoToFirst(uri: Uri, projection: Array<String>,
                                       selection: String?, selectionArgs: Array<String>?): Cursor? {
        val c = mContext.contentResolver.query(uri, projection,
                selection, selectionArgs, null) ?: return null
        if (!c.moveToFirst()) {
            c.close()
            return null
        }
        return c
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mp?.release()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
        //发送广播
        val sendStarted = Intent()
        sendStarted.action = ACTION_MUSIC_STARTED
        mContext.sendBroadcast(sendStarted)
    }

    fun over() {
        mMusicCursor?.close()
    }
}