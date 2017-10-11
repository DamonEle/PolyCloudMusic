package com.damon43.polycloudmusic.bean

/**
 * Created by lenovo on 2017/10/10.
 * 歌曲bean类
 */
class Song {
    var albumId: Long = -1
    var albumName: String = ""
    var artistId: Long = -1
    var artistName: String = ""
    var duration: Int = -1
    var id: Long = -1
    var title: String = ""
    var trackNumber: Int = -1

    constructor(id: Long, albumId: Long, artistId: Long, title: String, artistName: String,
                albumName: String, duration: Int, trackNumber: Int) {
        this.id = id
        this.albumId = albumId
        this.artistId = artistId
        this.title = title
        this.artistName = artistName
        this.albumName = albumName
        this.duration = duration
        this.trackNumber = trackNumber
    }

    override fun toString(): String {
        return "id:" + id + " albumId:" + albumId + " title:" + title + " artistName:" + artistName + " duration:" +
                duration + " trackNumber:" + trackNumber
    }
}