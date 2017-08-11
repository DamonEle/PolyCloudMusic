package com.damon43.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.damon43.common.baseinterface.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author damonmasty
 *         Created on 上午11:52 17-2-23.
 *         recyclerview 适配器基类
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {

    protected List<T> datas;
    protected Context mContext;
    protected OnRecyclerViewItemClickListener onItemClickListener;

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(datas.get(position));
    }

    @Override
    public BaseRecyclerViewAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate
                (onCreateView(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int position) {
        onBindView(datas.get(position), holder);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public abstract int onCreateView(int viewType);

    public abstract void onBindView(T t, BaseViewHolder holder);

    public abstract int getItemViewType(T t);

    protected class BaseViewHolder extends RecyclerView.ViewHolder {

        SparseArray<View> viewDatas;

        public BaseViewHolder(View itemView) {
            super(itemView);
            viewDatas = new SparseArray<>();
        }

        public TextView getTextView(int resId) {
            return getView(resId);
        }

        public ImageView getImageView(int resId) {
            return getView(resId);
        }

        public void setText(int resId, String strId) {
            getTextView(resId).setText(strId);
        }

        public <E extends View> E getView(int resId) {
            View view = viewDatas.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                viewDatas.put(resId, view);
            }
            return (E) view;
        }
    }
}
