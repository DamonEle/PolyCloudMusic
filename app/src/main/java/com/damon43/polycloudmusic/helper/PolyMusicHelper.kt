package com.damon43.polycloudmusic.helper

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.damon43.common.commonutils.LogUtils
import com.damon43.polycloudmusic.IPolyMusicInterface
import com.damon43.polycloudmusic.service.PolyMusicService
import java.util.*

/**
 * Created by lenovo on 2017/9/22.
 */
/**
 * 音乐操作类 服务管理类
 */
class PolyMusicHelper {

    /*静态块声明*/
    companion object {
        val mConnectionMap = WeakHashMap<Context, ServiceConnectedWrapper>()
        val emptyList = LongArray(0)
        /**当前服务远程操作代理类*/
        var mConnection: IPolyMusicInterface? = null

        /**
         * 返回
         */
        fun bindService(context: Context, connectionListener: ServiceConnection): ConnectionToken? {
            val toBind = Intent(context, PolyMusicService::class.java)
            //生成回调装饰者
            val wrapper = ServiceConnectedWrapper(context, connectionListener)
            if (context.bindService(toBind, wrapper, Service.BIND_AUTO_CREATE)) {
                mConnectionMap.put(context, wrapper)//保存服务连接回调 方便结束时注销连接
                return ConnectionToken(context)
            }
            //服务连接成功后生成本地proxy类，外界对服务端的任何请求统一让该类来完成
            return null
        }

        /**开始播放*/
        fun playMusic() {
            mConnection?.playMusic()
        }
        /**获得当前的播放队列*/
        fun getPlayQueue(): LongArray {
            return mConnection?.queue ?: emptyList
        }

        /**移除服务*/
        fun unBindService(token: ConnectionToken?) {
            if (token != null) {
                val unBind = mConnectionMap.remove(token.mToken) //移除并弹出
                if (unBind == null) return
                token.mToken.unbindService(unBind)
            }
        }


        /**
         * 服务连接回调装饰模式 生成proxy类统一代理
         */
        class ServiceConnectedWrapper constructor(val context: Context, val callback: ServiceConnection) : ServiceConnection {

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                //获取远程stub实例化客户端proxy
                mConnection = IPolyMusicInterface.Stub.asInterface(service)
                callback.onServiceConnected(name, service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                callback.onServiceDisconnected(name)
            }

        }

        class ConnectionToken constructor(val mToken: Context)

        fun playAll(mSongsIds: LongArray, position: Int, b: Boolean) {

            mConnection?.open(mSongsIds,position,-1,0)
        }

        fun next(){
            mConnection?.nextMusic()
        }
        fun back(){
            mConnection?.backMusic()
        }
    }
}