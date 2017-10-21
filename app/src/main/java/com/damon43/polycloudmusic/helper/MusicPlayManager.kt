package com.damon43.polycloudmusic.helper

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.media.MediaPlayer
import android.provider.MediaStore
import com.damon43.common.commonutils.LogUtils
import com.damon43.polycloudmusic.bean.MusicPlayTrack

/**
 * desc 管理音乐播放的各种相关操作
 * Created by lenovo on 2017/10/20.
 */
class MusicPlayManager(var mContext: Context) : MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener
        , MediaPlayer.OnCompletionListener {

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

    /**
     * 按队列播放音乐
     */
    fun playAll(list: List<Long>?, position: Int) {
        currentPos = position
        val id = list?.get(position) ?: 0//获取专辑id
        LogUtils.logD("id:" + id)
        val url = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , id) //获取专辑播放路径
        LogUtils.logD("url:" + url)

        mMusicList.add(MusicPlayTrack(id, -1, -1, -1, url.toString()))
        mMusicCursor = mContext.contentResolver.query(url, PROJECTION, null, null, null)
        mCurrentMediaPlayer.setDataSource(mContext, url)
        mCurrentMediaPlayer.setOnPreparedListener(MusicPlayManager@ this)
        mCurrentMediaPlayer.setOnErrorListener(MusicPlayManager@ this)
        mCurrentMediaPlayer.prepareAsync()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mp?.release()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    fun over() {
        mMusicCursor?.close()
    }
}