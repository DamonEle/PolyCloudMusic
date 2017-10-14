package com.damon43.polycloudmusic.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.damon43.common.base.BaseRecyclerViewAdapter
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.bean.Song

/**
 * desc
 * Created by lenovo on 2017/10/10.
 */
class SongListAdapter(context: Context, datas: List<Song>) : BaseRecyclerViewAdapter<Song>(context, datas) {
    override fun onCreateView(viewType: Int): Int = R.layout.item_rv_song

    override fun onBindView(t: Song, holder: BaseViewHolder) {
        holder.setText(R.id.tvSongName,t.title)
        holder.setText(R.id.tvSongAuthor,t.artistName)
    }

    override fun getItemViewType(t: Song): Int = 1
}