package com.damon43.polycloudmusic.helper.untls

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Song

/**
 * Created by lenovo on 2017/10/9.
 */
object AlbumLoader {
    fun getAllAlbum(context: Context): List<Album> =
            getAlbumsFromCursor(makeAlbumsCursor(context, null, null))

    /**通过获取的游标取得整列的song数据*/
    private fun getAlbumsFromCursor(makeSongsCursor: Cursor?): MutableList<Album> {
        val albums = mutableListOf<Album>()
        if (makeSongsCursor != null && makeSongsCursor.moveToFirst()) {
            do {
                val album = Album(makeSongsCursor.getLong(0), makeSongsCursor.getString(1)
                        , makeSongsCursor.getString(2), makeSongsCursor.getLong(3), makeSongsCursor.getInt(4)
                        , makeSongsCursor.getInt(5))
                Log.d("album", album.toString())
                albums.add(album)
            } while (makeSongsCursor.moveToNext())
        }
        makeSongsCursor?.close()
        return albums
    }

    /**按条件查询本地音乐*/
    private fun makeAlbumsCursor(context: Context, selection: String?, params: Array<String>?): Cursor {
        return context.contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, arrayOf
        ("_id", "album", "artist", "artist_id", "numsongs", "minyear"), selection, params, null);
    }

    /**
     * 获取音乐的专辑图片 需要专辑id
     */
    private fun getAlbumArt(context: Context, album_id: Int): String? {
        val mUriAlbums = "content://media/external/audio/albums"
        val projection = arrayOf("album_art")
        var cur = context.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null)
        var album_art: String? = null
        if (cur!!.getCount() > 0 && cur!!.getColumnCount() > 0) {
            cur!!.moveToNext()
            album_art = cur!!.getString(0)
        }
        cur!!.close()
        cur = null
        return album_art
    }
}