package com.damon43.common.commonutils;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author damonmasty
 *         Created on 下午10:23 17-3-8.
 *         通用recyclerview头部尾部view装饰者
 */

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int BASE_HEADER_KEY = 100000;
    public static final int BASE_FOOTER_KEY = 200000;


    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter adornAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adornAdapter) {
        this.adornAdapter = adornAdapter;
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(BASE_HEADER_KEY + mHeaderViews.size(), view);
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        mFooterViews.put(BASE_FOOTER_KEY + mFooterViews.size(), view);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            View view = mHeaderViews.get(viewType);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HeaderViewWrapper(view);
        } else if (mFooterViews.get(viewType) != null) {
            View view = mFooterViews.get(viewType);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewWrapper(view);
        }
        return adornAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPosition(position)) {
            return;
        } else if (isFooterViewPosition(position)) {
            return;
        }
        adornAdapter.onBindViewHolder(holder, position);
    }

    private boolean isFooterViewPosition(int position) {
        return position >= adornAdapter.getItemCount() + mHeaderViews.size();
    }

    private boolean isHeaderViewPosition(int position) {
        return position < mHeaderViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPosition(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPosition(position)) {
            return mFooterViews.keyAt(position - mHeaderViews.size() - adornAdapter.getItemCount());
        }
        return adornAdapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mHeaderViews.size() + mFooterViews.size() + adornAdapter.getItemCount();
    }

    protected class HeaderViewWrapper extends RecyclerView.ViewHolder {

        public HeaderViewWrapper(View itemView) {
            super(itemView);
        }
    }

    protected class FooterViewWrapper extends RecyclerView.ViewHolder {

        public FooterViewWrapper(View itemView) {
            super(itemView);
        }
    }
}
