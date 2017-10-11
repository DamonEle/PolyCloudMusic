package com.damon43.polycloudmusic.ui.main.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.damon43.common.base.BaseActivity
import com.damon43.common.commonutils.DensityUtil
import com.damon43.polycloudmusic.MainActivity
import com.damon43.polycloudmusic.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {


    val TIME = 3000L
    override fun initView() {
//        ivLogo.visibility = View.VISIBLE
//        tvPolytext.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        val set = AnimatorSet()
        val traY = DensityUtil.dip2px(WelcomeActivity@ this, 40f)
        val alphaAni = ObjectAnimator.ofFloat(ivLogo, "alpha", 0f, 1f)
        val alphaAni2 = ObjectAnimator.ofFloat(tvPolytext, "alpha", 0f, 1f)
        val traAni = ObjectAnimator.ofFloat(ivLogo, "translationY", ivLogo.translationY, ivLogo
                .translationY - traY)
        val traAni2 = ObjectAnimator.ofFloat(tvPolytext, "translationY", tvPolytext.translationY,
                tvPolytext.translationY - traY)
        val setPoly = AnimatorSet()
        setPoly.duration = 2000
        setPoly.playTogether(alphaAni2,traAni2)
        setPoly.start()
        set.playTogether(alphaAni, traAni)
        set.duration = 1300
        set.start()
        Handler().postDelayed({
            startActivity(Intent(WelcomeActivity@ this,
                    MainActivity::class.java))
            finish()
        }, TIME)
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
