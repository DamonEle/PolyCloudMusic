package com.damon43.polycloudmusic.helper.untls

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.damon43.polycloudmusic.bean.Song

/**
 * Created by lenovo on 2017/10/9.
 */
object SongLoader {
    fun getAllSongs(context: Context): List<Song> =
            getSongsFromCursor(makeSongsCursor(context, null, null))

    /**通过获取的游标取得整列的song数据*/
    private fun getSongsFromCursor(makeSongsCursor: Cursor?): MutableList<Song> {
        val songs = mutableListOf<Song>()
        if (makeSongsCursor != null && makeSongsCursor.moveToFirst()) {
            do {
                val id = makeSongsCursor.getLong(0)
                val title = makeSongsCursor.getString(1)
                val artist = makeSongsCursor.getString(2)
                val album = makeSongsCursor.getString(3)
                val duration = makeSongsCursor.getInt(4)
                val trackNumber = makeSongsCursor.getInt(5)
                val artistId = makeSongsCursor.getLong(6)
                val albumId = makeSongsCursor.getLong(7)
                val song = Song(id, albumId, artistId, title, artist, album, duration, trackNumber)
                Log.d("songs", song.toString())
                songs.add(song)
            } while (makeSongsCursor.moveToNext())
        }
        makeSongsCursor?.close()
        return songs
    }

    /**按条件查询本地音乐*/
    private fun makeSongsCursor(context: Context, selection: String?, params: Array<String>?): Cursor {
        var selectionStatement = "is_music=1 AND title != ''"

        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = selectionStatement + " AND " + selection
        }
        return context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf("_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id"),
                selectionStatement, params, null);
    }

    /**
     * 获取音乐的专辑图片 需要专辑id
     */
    private fun getAlbumArt(context: Context,album_id: Int): String? {
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