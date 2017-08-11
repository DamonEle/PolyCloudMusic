package com.damon43.polycloudmusic

import android.os.Bundle
import com.damon43.common.base.BaseActivity


class MainActivity : BaseActivity() {
    override fun initView() {

    }

    override fun initPresenter() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
