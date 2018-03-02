package com.damon43.polycloudmusic.helper.untls

import android.content.ContentUris
import android.net.Uri

/**
 * desc
 * Created by lenovo on 2017/10/17.
 */
object CustomDataLoader {

    /**
     * 根据专辑id来获得专辑图片
     */
    fun getAlbumArtUri(albumId: Long): Uri {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId)
    }

}