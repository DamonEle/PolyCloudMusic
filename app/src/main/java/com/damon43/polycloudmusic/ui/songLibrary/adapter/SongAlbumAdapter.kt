package com.damon43.polycloudmusic.ui.songLibrary.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.damon43.common.base.BaseRecyclerViewAdapter
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.helper.untls.CustomDataLoader

/**
 * desc
 * Created by lenovo on 2017/10/10.
 */
class SongAlbumAdapter(context: Context, datas: List<Album>) : BaseRecyclerViewAdapter<Album>(context, datas) {
    override fun onCreateView(viewType: Int): Int = R.layout.item_gv_album

    override fun onBindView(t: Album, holder: BaseViewHolder) {
        holder.setText(R.id.tvAlbumName,t.title)
        holder.setText(R.id.tvAlbumAuthor,t.artistName)
        Glide.with(mContext).load(CustomDataLoader.getAlbumArtUri(t.id))
                .placeholder(R.drawable.ic_music_album_default)
                .error(R.drawable.ic_music_album_default)
                .into(holder.getImageView(R.id.ivAlbumImg))
    }

    override fun getItemViewType(t: Album): Int = 1
}