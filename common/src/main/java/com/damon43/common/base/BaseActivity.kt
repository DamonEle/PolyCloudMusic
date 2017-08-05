package com.damon43.common.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.damon43.common.baserx.RxManager
import com.damon43.common.commonutils.ClassUtil

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * @author damonmasty
 * *         Created on 上午11:36 17-1-27.
 * *         基类activity 可以根据选择是否设计成mvp模式 实现initPresenter方法
 */

abstract class BaseActivity<T : BasePresenter<*, *>, E : BaseModel> : AppCompatActivity(), BaseView {

    var mPresenter: T ? =null
    var rxManager: RxManager ? =null
    protected var unbinder: Unbinder ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        unbinder = ButterKnife.bind(this) //依赖注入
        rxManager = RxManager()
        mPresenter = ClassUtil.getT<T>(this, 0) //实例化泛型presenter
        initPresenter()
        initView()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        rxManager?.clear()
        unbinder?.unbind()
        super.onDestroy()
    }

    abstract fun initView()

    abstract fun initPresenter()

    abstract val layoutId: Int

    override fun onLoaded() {

    }

    override fun onLoading() {

    }


    override fun onLoadFailed(e: Throwable, error: String) {

    }
}
