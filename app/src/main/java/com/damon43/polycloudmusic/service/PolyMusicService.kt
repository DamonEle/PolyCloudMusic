package com.damon43.polycloudmusic.service

import android.app.Service
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.IBinder
import android.provider.MediaStore
import com.damon43.common.commonutils.LogUtils
import com.damon43.polycloudmusic.IPolyMusicInterface
import com.damon43.polycloudmusic.bean.MusicPlayTrack
import com.damon43.polycloudmusic.helper.MusicPlayManager
import java.lang.ref.WeakReference

/**
 * Created by lenovo on 2017/9/22.
 * 播放音乐服务
 */
class PolyMusicService : Service() {

    /*保存着播放记录的集合*/
    val musics = mutableListOf<MusicPlayTrack>()
    lateinit var mMusicManager: MusicPlayManager
    /*服务端功能实现类*/
    var serviceStub: ServiceBinder? = null

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        serviceStub = ServiceBinder(PolyMusicService@ this)
        mMusicManager = MusicPlayManager(PolyMusicService@ this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder {
        return serviceStub!!.asBinder()
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun getMusicImg(): String? {
        return ""
    }

    private fun getAuthorName(): String? {
        return ""
    }

    private fun getSongName(): String? = ""

    private fun getPauseposition(): Long? = 0

    private fun getQueue(): LongArray? {
        return null
    }

    private fun getDuration(): Long? {
        return 0
    }

    private fun isPlaying(): Boolean {
        return false
    }

    private fun updatePlayList() {

    }

    private fun moveQueueItem(from: Int, to: Int) {

    }

    private fun setRepeatMode(repeatmode: Int) {

    }

    private fun enqueue(list: LongArray?, action: Int, sourceId: Long, sourceType: Int) {

    }

    private fun backMusic() {

    }

    private fun nextMusic() {

    }

    private fun stopMusic() {

    }

    private fun playMusic() {
        LogUtils.logD("play music!")
    }

    /**
     * 开始按当前队列播放音乐
     */
    private fun open(list: LongArray?, position: Int, sourceId: Long, sourceType: Int) {

        mMusicManager.playAll(list?.toList(),position)
    }

    private fun openFile(path: String?) {

    }


    /**
     * 远程服务端实现类 用于服务连接时返回
     */
    class ServiceBinder(val service: PolyMusicService) : IPolyMusicInterface.Stub() {

        /**服务端弱引用 防止内存泄露*/
        val weakService = WeakReference<PolyMusicService>(service)

        override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
        }

        override fun openFile(path: String?) {
            weakService.get()?.openFile(path)
        }

        override fun open(list: LongArray?, position: Int, sourceId: Long, sourceType: Int) {
            weakService.get()?.open(list, position, sourceId, sourceType)
        }

        override fun playMusic() {
            weakService.get()?.playMusic()
        }

        override fun stopMusic() {
            weakService.get()?.stopMusic()
        }

        override fun nextMusic() {
            weakService.get()?.nextMusic()
        }

        override fun backMusic() {
            weakService.get()?.backMusic()
        }

        override fun enqueue(list: LongArray?, action: Int, sourceId: Long, sourceType: Int) {
            weakService.get()?.enqueue(list, action, sourceId, sourceType)
        }

        override fun setRepeatMode(repeatmode: Int) {
            weakService.get()?.setRepeatMode(repeatmode)
        }

        override fun moveQueueItem(from: Int, to: Int) {
            weakService.get()?.moveQueueItem(from, to)
        }

        override fun playlistChanged() {
            weakService.get()?.updatePlayList()
        }

        override fun isPlaying(): Boolean = weakService.get()?.isPlaying() ?: false

        override fun getDuration(): Long = weakService.get()?.getDuration() ?: 0

        override fun getPauseposition(): Long = weakService.get()?.getPauseposition() ?: 0L

        override fun getQueue(): LongArray = weakService.get()?.getQueue() ?: kotlin.LongArray(0)

        override fun getSongName(): String = weakService.get()?.getSongName() ?: ""

        override fun getAuthorName(): String = weakService.get()?.getAuthorName() ?: ""

        override fun getMusicImg(): String = weakService.get()?.getMusicImg() ?: ""

    }
}