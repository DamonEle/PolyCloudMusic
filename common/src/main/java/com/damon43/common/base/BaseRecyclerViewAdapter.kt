package com.damon43.common.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.damon43.common.baseinterface.OnRecyclerViewItemClickListener

/**
 * @author damonmasty
 * *         Created on 上午11:52 17-2-23.
 * *         recyclerview 适配器基类
 */

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewAdapter<T>.BaseViewHolder> {

    var datas : MutableList<T> = mutableListOf()
        set(value) {
            this.datas.clear()
            this.datas.addAll(value)
            notifyDataSetChanged()
        }
    protected var mContext: Context
    protected var onItemClickListener: OnRecyclerViewItemClickListener? = null

    constructor(mContext: Context) {
        this.mContext = mContext
    }

    constructor(context: Context, datas: MutableList<T>) {
        this.mContext = context
        this.datas.addAll(datas)
    }


    fun addDatas(datas: List<T>) {
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = getItemViewType(datas!![position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewAdapter<T>.BaseViewHolder =
            BaseViewHolder(LayoutInflater.from(mContext).inflate(onCreateView(viewType), parent, false))

    override fun onBindViewHolder(holder: BaseRecyclerViewAdapter<T>.BaseViewHolder, position: Int) {
        onBindView(datas!![position], holder)
    }

    override fun getItemCount(): Int = if (datas == null) 0 else datas!!.size

    abstract fun onCreateView(viewType: Int): Int

    abstract fun onBindView(t: T, holder: BaseViewHolder)

    abstract fun getItemViewType(t: T): Int

    public inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var viewDatas: SparseArray<View>

        init {
            viewDatas = SparseArray<View>()
        }

        fun getTextView(resId: Int): TextView = getView(resId)

        fun getImageView(resId: Int): ImageView = getView(resId)

        fun setText(resId: Int, strId: String) {
            getTextView(resId).text = strId
        }

        fun <E : View> getView(resId: Int): E {
            var view: View? = viewDatas.get(resId)
            if (view == null) {
                view = itemView.findViewById(resId)
                viewDatas.put(resId, view)
            }
            return view as E
        }
    }
}
