package com.gcode.gmusic.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.gmusic.R
import com.example.gmusic.databinding.MainActivityBinding
import com.gcode.gmusic.manager.PlayManager
import com.gcode.gmusic.ui.fragment.MusicPlayFragment
import com.gcode.gmusic.ui.fragment.SettingsFragment
import com.gcode.gmusic.viewModel.MainActVM
import com.gcode.tools.utils.MsgWindowUtils
import com.permissionx.guolindev.PermissionX
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : FragmentActivity() {

    /**
     * Fragment页面的数量.
     */
    private val NUM_PAGES = 2

    /**
     * 获取binding对象
     */
    private lateinit var binding: MainActivityBinding

    private var vpAdapter: ScreenSlidePagerAdapter? = null

    private val actVM: MainActVM by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

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
                    MsgWindowUtils.showShortMsg(
                        this,
                        "These permissions are denied: $deniedList")
                }
            }

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        //initView()
        //创建适配器对象
        vpAdapter = ScreenSlidePagerAdapter(this)
        binding.fragmentVp.adapter = vpAdapter
        //设置布局管理器

        //设置每一项的点击事件
        binding.localMusicBottomIvLast.setOnClickListener {
            actVM.currentPlayPos.observe(this){
                if (it == 0) {
                    MsgWindowUtils.showShortMsg(this, "已经是第一首了，没有上一曲！")
                }else{
                    val cpp = it - 1
                    actVM.setCurrentPlayPos(cpp)
                    val lastBean = actVM.getMusicPyPos(cpp)
                    actVM.setCurrentPlayMusic(lastBean)
                    PlayManager.playMusicInMusicBean(lastBean.id.toLong())
                }
            }
        }
        binding.localMusicBottomIvPlay.setOnClickListener {
            actVM.currentPlayPos.observe(this){
                if (it == -1) {
                    MsgWindowUtils.showShortMsg(this, "请选择想要播放的音乐")
                }
                if (PlayManager.isPlaying()) {
                    PlayManager.pauseMusic()
                } else {
                    PlayManager.playMusic()
                }
            }
        }
        binding.localMusicBottomIvNext.setOnClickListener {
            actVM.currentPlayPos.observe(this){
                if (it == actVM.getMusicCount() - 1) {
                    MsgWindowUtils.showShortMsg(this, "已经是最后一首了，没有下一曲！")
                }else{
                    val cpp = it + 1
                    actVM.setCurrentPlayPos(cpp)
                    val nextBean = actVM.getMusicPyPos(cpp)
                    actVM.setCurrentPlayMusic(nextBean)
                    PlayManager.playMusicInMusicBean(nextBean.id.toLong())
                }
            }
        }

        actVM.currentPlayMusic.observe(this){
            binding.item = it
        }

        binding.localMusicControlLayout.setOnClickListener {
            startForResult.launch(Intent(this, MusicItemActivity::class.java))
        }

        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                binding.fragmentVp.currentItem = newIndex
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                binding.fragmentVp.currentItem = index
            }
        })

        binding.fragmentVp.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomBar.selectTabAt(position)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        PlayManager.stopMusic()
    }



//    private fun initView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onQueryTextSubmit(query: String): Boolean {
//                searchResultMusic = SearchSong.searchSongByName(mData, query)
//                mData.clear()
//                mData.addAll(searchResultMusic)
//                adapter!!.notifyDataSetChanged()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                mData.clear()
//                if (cacheMusicData.size < 1) {
//                    loadLocalMusicData()
//                    return false
//                }
//                mData.addAll(cacheMusicData)
//                adapter!!.notifyDataSetChanged()
//                return false
//            }
//        })
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_toolbar_setting -> {
                MsgWindowUtils.showShortMsg(this, "Hello")
            }

            R.id.main_toolbar_search -> {
                if (binding.searchView.visibility == View.GONE) {
                    binding.searchView.visibility = View.VISIBLE
                } else {
                    binding.searchView.visibility = View.GONE
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.fragmentVp.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.fragmentVp.currentItem = binding.fragmentVp.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = when(position){
            0-> MusicPlayFragment()
            1-> SettingsFragment()
            else -> MusicPlayFragment()
        }
    }
}