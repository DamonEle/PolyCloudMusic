package com.damon43.polycloudmusic.adapter

import android.content.Context
import com.damon43.common.base.BaseRecyclerViewAdapter
import com.damon43.polycloudmusic.bean.Song

/**
 * desc
 * Created by lenovo on 2017/10/10.
 */
class SongListAdapter(context: Context, datas: MutableList<Song>) : BaseRecyclerViewAdapter<Song>(context, datas) {
    override fun onCreateView(viewType: Int): Int = 1

    override fun onBindView(t: Song, holder: BaseViewHolder) {
    }

    override fun getItemViewType(t: Song): Int = 1
}