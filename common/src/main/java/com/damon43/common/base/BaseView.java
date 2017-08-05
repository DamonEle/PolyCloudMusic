package com.damon43.common.base;

/**
 * @author damonmasty
 *         Created on 上午11:25 17-1-27.
 */

public interface BaseView {
    void onLoaded();
    void onLoading();
    void onLoadFailed(Throwable e,String error);
}
