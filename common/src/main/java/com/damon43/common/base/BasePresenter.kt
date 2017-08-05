package com.damon43.common.base

/**
 * @author damonmasty
 * *         Created on 上午11:32 17-1-27.
 */

abstract class BasePresenter<T, E> {
    var mModel: T ? = null
    var mView: E ? = null

    fun setVM(t: T, e: E) {
        this.mModel = t
        this.mView = e
    }
}
