package com.damon43.polycloudmusic

import android.support.v7.widget.Toolbar
import com.damon43.common.base.BaseActivity
import org.jetbrains.anko.find

class MainActivity : BaseActivity() {

    var mToolBar : Toolbar ? = null

    override fun initView() {
        mToolBar = find(R.id.tb_main)
        mToolBar?.inflateMenu(R.menu.main_menu)
    }

    override fun initPresenter() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}
