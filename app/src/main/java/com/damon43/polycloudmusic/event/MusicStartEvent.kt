package com.damon43.polycloudmusic.event

/**
 * desc
 * Created by lenovo on 2017/12/22.
 */
class MusicStartEvent( action:String="", id:Long,var songName:String,var authorName:String,var albumImg:String):MusicEvent(action,id)