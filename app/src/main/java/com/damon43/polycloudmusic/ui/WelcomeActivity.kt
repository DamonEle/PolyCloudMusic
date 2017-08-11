package com.damon43.polycloudmusic.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.damon43.common.base.BaseActivity

import com.damon43.polycloudmusic.R

class WelcomeActivity : BaseActivity() {

    var mHandler  : Handler ? = null
    val TIME = 2000L
    override fun initView() {
        mHandler = Handler()
        var runnable = Runnable {  }
        mHandler.postDelayed({},TIME)
    }

    override fun initPresenter() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
}
