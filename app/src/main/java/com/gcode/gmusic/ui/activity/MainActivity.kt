package com.gcode.gmusic.ui.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.gmusic.R
import com.example.gmusic.databinding.ActivityMainBinding
import com.gcode.gmusic.model.MusicBean
import com.gcode.gmusic.ui.fragment.MusicPlayFragment
import com.gcode.gmusic.viewModel.MainActVM
import com.gcode.vasttools.activity.VastVbVmActivity
import com.gcode.vasttools.adapter.VastFragmentAdapter
import com.gcode.vasttools.extension.cast
import com.gcode.vasttools.utils.ToastUtils
import com.permissionx.guolindev.PermissionX
import nl.joery.animatedbottombar.AnimatedBottomBar

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/3/25 22:46
// Description:
// Documentation:

class MainActivity : VastVbVmActivity<ActivityMainBinding,MainActVM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //申请权限
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Log.i(this.localClassName, "All permissions are granted")
                } else {
                    ToastUtils.showShortMsg(
                        this,
                        "These permissions are denied: $deniedList")
                }
            }

        getBinding().fragmentVp.adapter = VastFragmentAdapter(this,ArrayList<Fragment>().apply {
            add(MusicPlayFragment())
        })

        //设置每一项的点击事件
        getBinding().localMusicBottomIvLast.setOnClickListener {
            when(getViewModel().currentPlayPos){
                0 ->
                    ToastUtils.showShortMsg(this, "已经是第一首了，没有上一曲！")
                -1 ->
                    ToastUtils.showShortMsg(this, "请选择想要播放的音乐")
                else-> {
                    val pos = getViewModel().currentPlayPos - 1
                    getViewModel().apply {
                        setCurrentPlayPos(pos)
                        playMusicInMusicBean(getViewModel().getMusicPyPos(pos))
                    }
                }
            }
        }

        getBinding().localMusicBottomIvPlay.setOnClickListener {
            if (getViewModel().currentPlayPos == -1) {
                ToastUtils.showShortMsg(this, "请选择想要播放的音乐")
            }else{
                if (getViewModel().isPlaying()) {
                    getViewModel().pauseMusic()
                    getBinding().localMusicBottomIvPlay.setImageResource(R.drawable.ic_play)
                } else {
                    getViewModel().playMusic()
                    getBinding().localMusicBottomIvPlay.setImageResource(R.drawable.ic_pause)
                }
            }
        }

        getBinding().localMusicBottomIvNext.setOnClickListener {
            when (getViewModel().currentPlayPos) {
                getViewModel().getMusicCount() - 1 ->
                    ToastUtils.showShortMsg(this, "已经是最后一首了，没有下一曲！")
                -1 ->
                    ToastUtils.showShortMsg(this, "请选择想要播放的音乐")
                else -> {
                    val pos = getViewModel().currentPlayPos + 1
                    getViewModel().apply {
                        setCurrentPlayPos(pos)
                        playMusicInMusicBean(getViewModel().getMusicPyPos(pos))
                    }
                }
            }
        }

        getViewModel().currentPlayMusic.observe(this){
            getBinding().localMusicBottomIvPlay.setImageResource(R.drawable.ic_pause)
            getBinding().item = cast<MusicBean>(it)
        }

        getBinding().bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                getBinding().fragmentVp.currentItem = newIndex
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                getBinding().fragmentVp.currentItem = index
            }
        })

        getBinding().fragmentVp.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                getBinding().bottomBar.selectTabAt(position)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getViewModel().releaseMediaPlayer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_toolbar_setting -> {
                ToastUtils.showShortMsg(this, "Hello")
            }

            R.id.main_toolbar_search -> {
                if (getBinding().searchView.visibility == View.GONE) {
                    getBinding().searchView.visibility = View.VISIBLE
                } else {
                    getBinding().searchView.visibility = View.GONE
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (getBinding().fragmentVp.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            getBinding().fragmentVp.currentItem = getBinding().fragmentVp.currentItem - 1
        }
    }
}