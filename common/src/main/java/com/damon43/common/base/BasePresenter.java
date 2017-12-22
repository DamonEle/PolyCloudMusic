package com.damon43.common.base;

import com.damon43.common.baserx.RxManager;

/**
 * @author damonmasty
 *         Created on 上午11:32 17-1-27.
 */

public abstract class BasePresenter<T, E> {
    public T mModel;
    public E mView;
    public RxManager rxManager;

    public void setVM(T t, E e) {
        this.mModel = t;
        this.mView = e;
        rxManager = new RxManager();
    }

    public void onDestory() {
        mView = null;
        mModel = null;
        rxManager.clear();
    }
}
