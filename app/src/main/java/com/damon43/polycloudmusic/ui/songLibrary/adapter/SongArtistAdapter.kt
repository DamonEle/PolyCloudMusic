package com.damon43.polycloudmusic.ui.songLibrary.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.damon43.common.base.BaseRecyclerViewAdapter
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.bean.Album
import com.damon43.polycloudmusic.bean.Artist
import com.damon43.polycloudmusic.helper.untls.CustomDataLoader

/**
 * desc
 * Created by lenovo on 2017/10/10.
 */
class SongArtistAdapter(context: Context, datas: List<Artist>) : BaseRecyclerViewAdapter<Artist>(context, datas) {
    override fun onCreateView(viewType: Int): Int = R.layout.item_gv_artist

    override fun onBindView(t: Artist, holder: BaseViewHolder) {
        holder.setText(R.id.tvArtistName,t.name)
        holder.setText(R.id.tvArtistAuthor,t.albumCount.toString())
        Glide.with(mContext).load(CustomDataLoader.getAlbumArtUri(t.id))
                .placeholder(R.drawable.ic_music_album_default)
                .error(R.drawable.ic_music_album_default)
                .into(holder.getImageView(R.id.ivArtistImg))
    }

    override fun getItemViewType(t: Artist): Int = 1
}