package com.damon43.common.base;

/**
 * @author damonmasty
 *         Created on 上午11:32 17-1-27.
 */

public abstract class BasePresenter<T, E> {
    public T mModel;
    public E mView;

    public void setVM(T t, E e) {
        this.mModel = t;
        this.mView = e;
    }
}
