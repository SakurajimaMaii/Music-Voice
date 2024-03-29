/*
 * Copyright 2022 Vast Gui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.govast.vmusic.ui.activity

import android.Manifest
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.os.BuildCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import cn.govast.vasttools.activity.VastVbVmActivity
import cn.govast.vasttools.adapter.VastFragmentAdapter
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.utils.*
import cn.govast.vmusic.R
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.broadcast.MusicBroadcast
import cn.govast.vmusic.constant.Order
import cn.govast.vmusic.constant.UpdateKey.DURATION_KEY
import cn.govast.vmusic.constant.UpdateKey.PLAY_STATE_KEY
import cn.govast.vmusic.constant.UpdateKey.PROGRESS_KEY
import cn.govast.vmusic.databinding.ActivityMainBinding
import cn.govast.vmusic.service.musicplay.MusicService
import cn.govast.vmusic.sharedpreferences.UserSp
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.ui.base.sendOrderIntent
import cn.govast.vmusic.ui.fragment.DownloadFragment
import cn.govast.vmusic.ui.fragment.MusicPlayFragment
import cn.govast.vmusic.ui.fragment.RankingFragment
import cn.govast.vmusic.viewModel.MainSharedVM
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.permissionx.guolindev.PermissionX
import nl.joery.animatedbottombar.AnimatedBottomBar
import java.text.DecimalFormat
import java.util.*


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/3/25 22:46
// Description:
// Documentation:

class MainActivity : VastVbVmActivity<ActivityMainBinding, MainSharedVM>(), UIStateListener {

    /** 监听Service发送的广播 */
    private inner class MainReceiver : MusicBroadcast() {

        override fun onProgress(intent: Intent) {
            intent.getLongExtra(DURATION_KEY, 0).apply {
                getViewModel().mCurrentDuration = this
            }
            intent.getFloatExtra(PROGRESS_KEY, 0f).apply {
                if (!this.isNaN()) {
                    getBinding().musicProgress.setCurrentNum((this * 100).toDouble())
                    getViewModel().mCurrentProgress = this
                }
            }
        }

        override fun onPlayState(intent: Intent) {
            val state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getSerializable(PLAY_STATE_KEY, MusicService.PlayState::class.java)
            } else {
                @Suppress("DEPRECATION")
                cast(intent.extras?.getSerializable(PLAY_STATE_KEY))
            }
            state?.also {
                when (it) {
                    MusicService.PlayState.PLAYING ->
                        getBinding().musicPlayBtn.setImageResource(R.drawable.ic_fill_0_pause)

                    MusicService.PlayState.PAUSE ->
                        getBinding().musicPlayBtn.setImageResource(R.drawable.ic_fill_0_play_arrow)

                    MusicService.PlayState.NOPLAYING ->
                        getBinding().musicPlayBtn.setImageResource(R.drawable.ic_fill_0_play_arrow)
                }
                getViewModel().mCurrentPlayState = it
            } ?: getSnackbar().setText("未获取到播放状态").show()
        }
    }

    /** 用于和 [MusicService] 交流 */
    private inner class MusicServiceConn : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            cast<MusicService.MusicBinder>(service).getService().apply {
                registerMusicListener {
                    updateCurrentMusicList = { songs, playState ->
                        getViewModel().apply {
                            updateMusicList(songs, playState)
                        }
                    }

                    updateCurrentMusic = { musicWrapper ->
                        getViewModel().setCurrentMusic(musicWrapper)
                    }
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.e(getDefaultTag(), "$name 连接失败")
        }
    }

    private val mMainReceiver by lazy {
        MainReceiver()
    }

    private val mMusicServiceConn by lazy {
        MusicServiceConn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivity(this)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMainReceiver,
            IntentFilter(BConstant.ACTION_UPDATE)
        )
        bindService(
            Intent(this, MusicService::class.java).setType(getDefaultTag()),
            MusicServiceConn(),
            Context.BIND_AUTO_CREATE
        )
        // 申请权限
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
                        "These permissions are denied: $deniedList"
                    )
                }
            }
        initUI()
        initUIState()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMainReceiver)
        unbindService(mMusicServiceConn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_appbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_toolbar_search -> {
                if (getBinding().musicSearch.visibility == View.GONE) {
                    getBinding().musicSearch.visibility = View.VISIBLE
                } else {
                    getBinding().musicSearch.visibility = View.GONE
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    @androidx.annotation.OptIn(BuildCompat.PrereleaseSdkCheck::class)
    override fun onBackPressed() {
        if (getBinding().fragmentVp.currentItem == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (BuildCompat.isAtLeastT()) {
                    onBackInvokedDispatcher.registerOnBackInvokedCallback(
                        OnBackInvokedDispatcher.PRIORITY_DEFAULT
                    ) {
                        ActivityUtils.exitApp()
                    }
                } else {
                    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            ActivityUtils.exitApp()
                        }
                    })
                }
            } else {
                ActivityUtils.exitApp()
            }
        } else {
            getBinding().fragmentVp.currentItem = getBinding().fragmentVp.currentItem - 1
        }
    }

    override fun initUI() {
        // 设置状态栏
        setSupportActionBar(getBinding().topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getBinding().fragmentVp.adapter = VastFragmentAdapter(this, ArrayList<Fragment>().apply {
            add(MusicPlayFragment())
            add(RankingFragment())
            add(DownloadFragment())
        })
        getBinding().musicPlayBtn.setOnClickListener {
            sendOrderIntent(Order.PLAY_OR_PAUSE)
        }
        getBinding().bottomBar.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
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
        getBinding().topAppBar.apply {
            setNavigationOnClickListener {
                getBinding().drawerlayout.open()
            }
        }
        getBinding().musicSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.apply {
                    // 发送查询广播
                    sendOrderIntent(Order.SEARCH) {
                        it.putExtra(MusicService.NAME, this)
                    }
                    getBinding().musicLoading.show()
                }
                return false
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        getBinding().musicControl.setOnClickListener {
            val intent = Intent(this, MusicActivity::class.java).also {
                getViewModel().mCurrentMusic.value?.apply {
                    this.currentProgress = getViewModel().mCurrentProgress
                    val bundle = Bundle().also { bundle ->
                        bundle.putSerializable(MusicActivity.CURRENT_MUSIC_KEY, this)
                    }
                    it.putExtras(bundle)
                }
            }
            startActivity(intent)
        }
        // 设置进度指示器
        // https://github.com/material-components/material-components-android/blob/master/docs/components/ProgressIndicator.md
        getBinding().musicLoading.apply {
            showAnimationBehavior = BaseProgressIndicator.SHOW_INWARD
            hideAnimationBehavior = BaseProgressIndicator.HIDE_INWARD
            setVisibilityAfterHide(View.GONE)
        }
        getBinding().navigationView.apply {
            setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.app_setting -> {
                        val intent = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(intent)
                    }
                }
                return@setNavigationItemSelectedListener true
            }
        }
        initUIState()
        updateUserProfile()
    }

    override fun initUIState() {
        getViewModel().mCurrentMusic.observe(this) {
            getBinding().musicPlayWrapper = it
            Glide.with(this).load(it.music.albumArt).into(
                getBinding().musicAlbum
            )
        }

        getViewModel().mProgressState.observe(this) {
            if (it == MainSharedVM.ProgressState.HIDE) {
                getBinding().musicLoading.hide()
            }
        }
    }

    /** 更新用户信息 */
    private fun updateUserProfile() {
        getBinding().navigationView.getHeaderView(0).apply {
            val avatarUrl = UserSp.getSp().getString("avatarUrl", null)?.replace("http", "https")
            Glide.with(this).load(avatarUrl).into(
                findViewById<ShapeableImageView>(R.id.avatar)
            )
            val backgroundUrl =
                UserSp.getSp().getString("backgroundUrl", null)?.replace("http", "https")
            Glide.with(this).load(backgroundUrl).into(
                findViewById<ShapeableImageView>(R.id.background)
            )
            findViewById<MaterialTextView>(R.id.userName).text =
                UserSp.getSp().getString("nickname", null)
            val userId = DecimalFormat().parse(
                Double.fromBits(UserSp.getSp().getLong("userId", 0L)).toString()
            )?.toString() ?: ""
            findViewById<MaterialTextView>(R.id.province).text =
                String.format(Locale.CHINA, ResUtils.getString(R.string.format_userid), userId)
        }
    }

}