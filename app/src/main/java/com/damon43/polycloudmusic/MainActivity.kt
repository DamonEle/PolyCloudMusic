package com.damon43.polycloudmusic

import android.support.v7.widget.Toolbar
import com.damon43.common.base.BaseActivity
import org.jetbrains.anko.find

class MainActivity : BaseActivity() {

//    https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502884801042&di=eff74b36416ad98e300a4e9c88cefb3a&imgtype=0&src=http%3A%2F%2Fwww.lolshipin.com%2Fuploads%2Fallimg%2F150414%2F23-1504141519350-L.jpg
    var mToolBar : Toolbar ? = null

    override fun initView() {
        mToolBar = find(R.id.tb_main)
        mToolBar!!.inflateMenu(R.menu.main_menu)
    }

    override fun initPresenter() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}
