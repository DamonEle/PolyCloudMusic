package com.damon43.polycloudmusic.bean

/**
 * Created by lenovo on 2017/10/10.
 * 歌曲bean类
 */
data class Song(val id: Long =-1, val albumId: Long = -1, val artistId: Long = -1, val title: String
                 = "", val artistName: String = "",val  albumName: String = "",
                val duration: Int = -1, val trackNumber: Int = -1)