package com.damon43.polycloudmusic.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.damon43.common.base.BaseActivity
import com.damon43.polycloudmusic.MainActivity

import com.damon43.polycloudmusic.R

class WelcomeActivity : BaseActivity() {

    val TIME = 2000L
    override fun initView() {
         Handler().postDelayed(Runnable { startActivity(Intent(WelcomeActivity@this,
                MainActivity::class.java))
            finish()
         },TIME)
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
