package com.damon43.polycloudmusic.helper.untls

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Artist

/**
 * Created by lenovo on 2017/10/9.
 */
object ArtistLoader {
    fun getAllArtist(context: Context): List<Artist> =
            getArtistsFromCursor(makeArtistsCursor(context, null, null))

    /**通过获取的游标取得整列的song数据*/
    private fun getArtistsFromCursor(makeSongsCursor: Cursor?): MutableList<Artist> {
        val albums = mutableListOf<Artist>()
        if (makeSongsCursor != null && makeSongsCursor.moveToFirst()) {
            do {
                val artist = Artist(makeSongsCursor.getLong(0), makeSongsCursor.getString(1)
                        , makeSongsCursor.getInt(2), makeSongsCursor.getInt(3))
                albums.add(artist)
            } while (makeSongsCursor.moveToNext())
        }
        makeSongsCursor?.close()
        return albums
    }

    /**按条件查询本地音乐*/
    private fun makeArtistsCursor(context: Context, selection: String?, params: Array<String>?): Cursor {
        return context.contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                arrayOf("_id", "artist", "number_of_albums", "number_of_tracks"), selection,
                params, MediaStore.Audio.Artists.DEFAULT_SORT_ORDER)

    }

}