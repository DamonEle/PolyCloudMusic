package com.damon43.polycloudmusic.helper

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.damon43.common.commonutils.LogUtils
import com.damon43.polycloudmusic.base.Constant
import com.damon43.polycloudmusic.bean.MusicPlayTrack
import com.damon43.polycloudmusic.helper.untls.SongLoader

/**
 * desc 管理音乐播放的各种相关操作
 * Created by lenovo on 2017/10/20.
 */
class MusicPlayManager(var mContext: Context) : MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener
        , MediaPlayer.OnCompletionListener {

    val ACTION_MUSIC_STARTED = "ACTION_MUSIC_STARTED"
    lateinit var mCurrentSong: MusicPlayTrack

    /*当前播发*/
    private var mCurrentMediaPlayer = MediaPlayer()

    /*需要提取的数据*/
    val PROJECTION = arrayOf("audio._id AS _id", MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID)

    var mCurrentIsPause = false
    var mMusicCursor: Cursor? = null
    var currentPos = 0
    var mCurrentMusicId = 0L
    /*播发轨迹*/
    var mMusicTrackList = mutableListOf<MusicPlayTrack>()
    /*播放列表*/
    var mMusicList = mutableListOf<Long>()

    val isPlaying = true
    private val SONG_ID_COLIDX = 0
    private val ALBUM_ID_COLIDX = 6
    private val SONG_TITLE_COLIDX = 3
    private val SONG_AUTHOR_COLIDX = 1
    /**
     * 按队列播放音乐
     * 1.假如没有播放中 正常播放
     * 2.      有播放中  a.如果与播放的歌曲id一致，重新播放 b.不一致，初始化播放器设置并播放
     * onComple才可以改变游标
     */
    fun playAll(list: LongArray?, position: Int) = if (currentPos != position) {
        mMusicList = list!!.toMutableList()
        if (mCurrentMediaPlayer.isPlaying) mCurrentMediaPlayer.pause()
        mCurrentMediaPlayer = updateCursor(position)
        mCurrentMediaPlayer.prepareAsync()
    } else {
        val sendState = Intent()
        val extra = Bundle()
        extra.putLong(Constant.BUNDLE_KEY_SONG_ID, mCurrentSong.id)
        if (mCurrentMediaPlayer.isPlaying) {
            mCurrentMediaPlayer.pause()
            sendState.action = Constant.ACTION_MUSIC_PAUSE
        } else {
            mCurrentMediaPlayer.start()
            sendState.action = Constant.ACTION_MUSIC_RESTART
        }
        sendState.putExtra(Constant.BUNDLE_KEY_SONG_INFO, extra)
        mContext.sendBroadcast(sendState)
    }

    /** 同步游标指向对应位置的music 重置media*/
    private fun updateCursor(position: Int): MediaPlayer {
        currentPos = position
        mCurrentMusicId = mMusicList?.get(position) //获取专辑id
        LogUtils.logD("播放id:" + mCurrentMusicId)
        //获取携带音乐全部信息的游标
        mMusicCursor = openCursorAndGoToFirst(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                PROJECTION, "_id=" + mCurrentMusicId, null)
        val url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() + "/" + ((mMusicCursor?.getLong(SONG_ID_COLIDX) ?: 1).toString())

        val readyPlayMedia = MediaPlayer()
        readyPlayMedia.setDataSource(url)
        readyPlayMedia.setOnPreparedListener(MusicPlayManager@ this)
        readyPlayMedia.setOnErrorListener(MusicPlayManager@ this)
        readyPlayMedia.setOnCompletionListener(MusicPlayManager@ this)
        return readyPlayMedia
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
        //设置下一曲
        mCurrentMediaPlayer = updateCursor(++currentPos)
        mCurrentMediaPlayer.prepareAsync()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mp?.release()
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        LogUtils.logD("播放已经准备好")
        mp?.start()
        //发送广播
        val sendStarted = Intent()
        sendStarted.action = Constant.ACTION_MUSIC_START
        val url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() + "/" + ((mMusicCursor?.getLong(SONG_ID_COLIDX) ?: 1).toString())
        val albumId = mMusicCursor?.getLong(SONG_ID_COLIDX) ?: -1
        val title = mMusicCursor?.getString(SONG_TITLE_COLIDX) ?: "<null>"
        val author = mMusicCursor?.getString(SONG_AUTHOR_COLIDX) ?: "<null>"
        val music = MusicPlayTrack(mCurrentMusicId, albumId,
                SongLoader.getAlbumArt(mContext, albumId.toInt()), title, author, url)
        mMusicTrackList.add(music)
        mCurrentSong = music

        val extra = Bundle()
        extra.putLong(Constant.BUNDLE_KEY_SONG_ID, mCurrentSong.id)
        extra.putString(Constant.BUNDLE_KEY_SONG_NAME, mCurrentSong.title)
        extra.putString(Constant.BUNDLE_KEY_SONG_AUTHOR, mCurrentSong.author)
        extra.putString(Constant.BUNDLE_KEY_SONG_IMG_URL, mCurrentSong.albumImg)
        sendStarted.putExtra(Constant.BUNDLE_KEY_SONG_INFO, extra)

        mContext.sendBroadcast(sendStarted)
    }

    fun over() {
        mMusicCursor?.close()
    }

    fun backMusic() {
        if (mCurrentMediaPlayer.isPlaying) mCurrentMediaPlayer.pause()
        mCurrentMediaPlayer = updateCursor(--currentPos)
        mCurrentMediaPlayer.prepareAsync()
    }

    fun nextMusic() {
        if (mCurrentMediaPlayer.isPlaying) mCurrentMediaPlayer.pause()
        mCurrentMediaPlayer = updateCursor(++currentPos)
        mCurrentMediaPlayer.prepareAsync()
    }
}