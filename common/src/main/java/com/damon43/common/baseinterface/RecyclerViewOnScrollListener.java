package com.damon43.common.baseinterface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author damonmasty
 *         Created on 下午10:49 17-2-21.
 *         recyclerview滑动监听
 */

public abstract class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
    private boolean loading;
    private int previousTotal = 0;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public RecyclerViewOnScrollListener(LinearLayoutManager mLinearLayoutManager) {
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        totalItemCount = mLinearLayoutManager.getItemCount();
        visibleItemCount = recyclerView.getChildCount();

        if (!loading && (totalItemCount - firstVisibleItem) <= visibleItemCount) {
            //滑动到底部 <就是上拉
            loading = true;
            currentPage++;
            onLoadMore(currentPage);
        }
        if (loading) {
            if (totalItemCount > previousTotal) {
                //代表可能有新数据下来
                previousTotal = totalItemCount;
                loading = false;
            }
        }

    }

    protected abstract void onLoadMore(int page);

    /**
     * 防止请求返回数据为空的补救方案
     */
    public void reset() {
        loading = false;
    }
}
