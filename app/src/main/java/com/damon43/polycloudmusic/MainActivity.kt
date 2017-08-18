package com.damon43.polycloudmusic

import android.support.v4.app.FragmentTransaction
import android.view.Gravity
import android.view.View
import com.damon43.common.base.BaseActivity
import com.damon43.common.base.BaseFragment
import com.damon43.polycloudmusic.ui.SongLibraryFragment
import com.damon43.polycloudmusic.ui.main.FoldersFragment
import com.damon43.polycloudmusic.ui.main.PlayListFragment
import com.damon43.polycloudmusic.ui.main.PlayQueueFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_left.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), View.OnClickListener {

    val foldersFragment = FoldersFragment()
    val playListFragment = PlayListFragment()
    val playQueueFragment = PlayQueueFragment()
    val songLibraryFragment = SongLibraryFragment()

    var fromFragment: BaseFragment? = null

//    https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502884801042&di=eff74b36416ad98e300a4e9c88cefb3a&imgtype=0&src=http%3A%2F%2Fwww.lolshipin.com%2Fuploads%2Fallimg%2F150414%2F23-1504141519350-L.jpg


    fun setUpFragment() {
        val fr = supportFragmentManager.beginTransaction()
        fr.add(R.id.flContent, playQueueFragment, "playQueueFragment")
        fr.add(R.id.flContent, playListFragment, "playListFragment")
        fr.add(R.id.flContent, foldersFragment, "foldersFragment")
        fr.add(R.id.flContent, songLibraryFragment, "songLibraryFragment")
        fr.commit()
        switchTo(0)
    }

    override fun initView() {
        tbMain.inflateMenu(R.menu.main_menu)

        tbMain.setNavigationOnClickListener { dlContent.openDrawer(Gravity.START, true) }

        tvLibrary.setOnClickListener(MainActivity@ this)
        tvPlayList.setOnClickListener(MainActivity@ this)
        tvFolders.setOnClickListener(MainActivity@ this)
        tvPlayQueue.setOnClickListener(MainActivity@ this)
        setUpFragment()
    }

    override fun initPresenter() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvLibrary -> switchTo(0)
            R.id.tvPlayList -> switchTo(1)
            R.id.tvFolders -> switchTo(2)
            R.id.tvPlayQueue -> switchTo(3)
            R.id.tvPlaying -> toast("播放中")
            R.id.tvSetting -> toast("设置")
            R.id.tvAbout -> toast("关于")
        }
    }

    private fun switchTo(to: Int) {
        val fr = supportFragmentManager.beginTransaction()
        when (to) {
            0 -> fr.hide(foldersFragment).hide(playListFragment).hide(playQueueFragment).show(
                    songLibraryFragment).commit()
            1 -> fr.hide(foldersFragment).show(playListFragment).hide(playQueueFragment).hide(
                    songLibraryFragment).commit()
            2 -> fr.show(foldersFragment).hide(playListFragment).hide(playQueueFragment).hide(
                    songLibraryFragment).commit()
            3 -> fr.hide(foldersFragment).hide(playListFragment).show(playQueueFragment).hide(
                    songLibraryFragment).commit()
        }
    }
}
