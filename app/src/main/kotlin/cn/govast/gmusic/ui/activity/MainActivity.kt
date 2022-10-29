package cn.govast.gmusic.ui.activity

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import cn.govast.gmusic.R
import cn.govast.gmusic.databinding.ActivityMainBinding
import cn.govast.gmusic.network.repository.UserRepository
import cn.govast.gmusic.service.MusicBackgroundService
import cn.govast.gmusic.sharedpreferences.UserSp
import cn.govast.gmusic.ui.base.Order
import cn.govast.gmusic.ui.base.UIStateListener
import cn.govast.gmusic.ui.base.sendOrderIntent
import cn.govast.gmusic.ui.fragment.MusicPlayFragment
import cn.govast.gmusic.viewModel.MainSharedVM
import cn.govast.music.MusicPlayState
import cn.govast.vasttools.activity.VastVbVmActivity
import cn.govast.vasttools.adapter.VastFragmentAdapter
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.utils.AppUtils
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vasttools.utils.ToastUtils
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
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

    companion object {
        val ACTION_CONTROL = "${AppUtils.getPackageName()}.control" //控制播放、暂停
        val ACTION_UPDATE = "${AppUtils.getPackageName()}.update" //更新界面显示
    }

    /** 用于和 [MusicBackgroundService] 交流 */
    private inner class MusicServiceConn : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            cast<MusicBackgroundService.MusicBinder>(service).mService.apply {
                registerMusicListener {
                    onMusicLoaded = {
                        getViewModel().updateMusicList(it)
                    }
                    onMusicPlay = {
                        getViewModel().setCurrentMusic(it)
                    }
                    onProgress = {
                        if (!it.isNaN()) {
                            getBinding().musicProgress.setCurrentNum((it * 100).toDouble())
                        }
                    }
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.e("MainActivity", "$name 连接失败")
        }
    }

    private val musicServiceConn by lazy {
        MusicServiceConn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService(
            Intent(this, MusicBackgroundService::class.java),
            musicServiceConn,
            Context.BIND_AUTO_CREATE
        )
        // 设置状态栏
        setSupportActionBar(getBinding().topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        initUser()
        initUI()
        initUIState()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(musicServiceConn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_toolbar_setting -> {
                ToastUtils.showShortMsg("Hello")
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

    private fun initUser() {
        getRequestBuilder()
            .suspendWithListener({ UserRepository.checkLoginState() }) {
                onSuccess = {
                    it.data.profile?.apply {
                        UserSp.writeUser(this)
                    }
                }
                onError = {
                    getSnackbar().setText(it?.message ?: "用户信息初始化失败").show()
                }
                onEmpty = {
                    getSnackbar().setText("用户信息初始化失败").show()
                }
            }
    }

    override fun initUI() {
        getBinding().fragmentVp.adapter = VastFragmentAdapter(this, ArrayList<Fragment>().apply {
            add(MusicPlayFragment())
        })
        getBinding().localMusicBottomIvPlay.setOnClickListener {
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
        getBinding().searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.apply {
                    // 发送查询广播
                    sendOrderIntent(Order.SEARCH) {
                        it.putExtra(MusicBackgroundService.NAME, this)
                    }
                }
                return false
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        initUIObserver()
        updateUserProfile()
    }

    override fun initUIState() {
        getViewModel().mPlayState.observe(this) { state ->
            when (state) {
                MusicPlayState.PLAY -> {
                    getBinding().localMusicBottomIvPlay.setImageResource(R.drawable.ic_pause)
                }
                MusicPlayState.PAUSE -> {
                    getBinding().localMusicBottomIvPlay.setImageResource(R.drawable.ic_play)
                }
                else -> {}
            }
        }
    }

    override fun initUIObserver() {
        getViewModel().mCurrentMusic.observe(this) {
            getBinding().song = it
//            Glide.with(this).load(it.albumUrl).into(
//                getBinding().albumArt
//            )
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