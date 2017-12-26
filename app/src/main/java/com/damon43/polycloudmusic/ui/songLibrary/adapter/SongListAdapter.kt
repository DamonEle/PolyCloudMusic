package com.damon43.polycloudmusic.ui.songLibrary.adapter

import android.content.Context
import com.damon43.common.base.BaseRecyclerViewAdapter
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.bean.Song
import com.damon43.polycloudmusic.event.MusicStartEvent
import com.damon43.polycloudmusic.helper.PolyMusicHelper
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * desc
 * Created by lenovo on 2017/10/10.
 */
class SongListAdapter(context: Context, datas: List<Song>) : BaseRecyclerViewAdapter<Song>(context, datas) {

    var mSongsIds: List<Long>? = null
    var mCurrentPlaySongId = 0L

    init {
        getSongsId()
    }

    private fun getSongsId() {
        Observable.from(datas)
                .map { it -> it.id }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { mSongsIds = it }
    }

    override fun onCreateView(viewType: Int): Int = R.layout.item_rv_song

    override fun onBindView(t: Song, holder: BaseViewHolder) {
        val iv = holder.getImageView(R.id.ivItemPlay)
        if (t.id == mCurrentPlaySongId) iv.
                setImageResource(R.drawable.ic_bottom_play) else iv.
                setImageResource(R.drawable.ic_bottom_play)
        holder.setText(R.id.tvSongName, t.title)
        holder.setText(R.id.tvSongAuthor, t.artistName)
        holder.itemView.setOnClickListener {
            PolyMusicHelper.playAll(mSongsIds!!.toLongArray(), holder.position
                    , false)
        }
    }

    fun notifeCurrentPlay(event:MusicStartEvent) {
        mCurrentPlaySongId = event.id
        notifyDataSetChanged()
    }

    override fun getItemViewType(t: Song): Int = 1
}