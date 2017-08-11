package com.damon43.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.damon43.common.baserx.RxManager;
import com.damon43.common.commonutils.ClassUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author damonmasty
 *         Created on 上午11:36 17-1-27.
 *         基类activity 可以根据选择是否设计成mvp模式 实现initPresenter方法
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public RxManager rxManager;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this); //依赖注入
        rxManager = new RxManager();
        initPresenter();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        rxManager.clear();
        unbinder.unbind();
        super.onDestroy();
    }

    public abstract void initView();

    public abstract void initPresenter();

    public abstract int getLayoutId();

    @Override
    public void onLoaded() {

    }

    @Override
    public void onLoading() {

    }


    @Override
    public void onLoadFailed(Throwable e,String error) {

    }
}
