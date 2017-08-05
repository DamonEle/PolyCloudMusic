package com.damon43.common.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.damon43.common.baserx.RxManager
import com.damon43.common.commonutils.ClassUtil
import com.damon43.common.commonutils.LogUtils

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * @author damonmasty
 * *         Created on 下午4:55 17-1-28.
 * *
 * * mvp示例
 * //    @Override
 * //    public void initPresenter() {
 * //        mPresenter.setVM(this, ClassUtils.get(this,1));
 * //    }
 */

abstract class BaseFragment<T : BasePresenter<*, *>, E : BaseModel> : Fragment(), BaseView {
    var contentView: View ? =null
    var mPresenter: T ? =null
    var rxManager: RxManager ? =null
    var mContext: Context ? =null
    var mUnbinder: Unbinder ? =null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater!!.inflate(layoutResource, container, false)
//        mUnbinder = ButterKnife.bind(this, contentView) //依赖注入
        rxManager = RxManager()
        mContext = this.context
        mPresenter = ClassUtil.getT<T>(this, 0) //实例化泛型presenter
        initPresenter()
        initView()
        return contentView
    }

    abstract val layoutResource: Int
    override fun onLoaded() {

    }

    abstract fun initView()

    abstract fun initPresenter()
    override fun onLoading() {

    }

    override fun onLoadFailed(e: Throwable, error: String) {
        LogUtils.logE(e, error)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxManager?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnbinder?.unbind()
    }
}
