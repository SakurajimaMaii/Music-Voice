package cn.govast.vmusic.ui.activity

import android.content.*
import android.os.Build
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.vmusic.R
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.broadcast.MusicBroadcast
import cn.govast.vmusic.constant.UpdateKey
import cn.govast.vmusic.databinding.ActivityMusicBinding
import cn.govast.vmusic.service.MusicService
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.viewModel.MainSharedVM
import cn.govast.vmusic.viewModel.MusicVM
import cn.govast.vasttools.activity.VastVbVmActivity
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.nothing_to_do
import com.bumptech.glide.Glide

class MusicActivity : VastVbVmActivity<ActivityMusicBinding, MusicVM>(), UIStateListener {

    companion object {
        const val CURRENT_MUSIC_KEY = "current_music_key"
        const val PROGRESS_KEY = "progress_key"
    }

    private inner class MusicReceiver : MusicBroadcast() {
        override fun onMusicLoaded(intent: Intent) {
            // 无需为加载做任何操作
            nothing_to_do()
        }

        override fun onMusicPlay(intent: Intent) {
            // 由MainActivity传递
            nothing_to_do()
        }

        override fun onProgress(intent: Intent) {
            intent.getFloatExtra(UpdateKey.PROGRESS_KEY, 0f).apply {
                if (!this.isNaN()) {
                    getBinding().musicProgress.setCurrentNum((this * 100).toDouble())
                }
            }
        }

        override fun onPlayState(intent: Intent) {
            val state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getSerializable(
                    UpdateKey.PLAY_STATE_KEY,
                    MusicService.PlayState::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                cast(intent.extras?.getSerializable(UpdateKey.PLAY_STATE_KEY))
            }
            state?.apply {
                when (state) {
                    MusicService.PlayState.PLAYING ->
                        getBinding().musicPlay.setImageResource(R.drawable.ic_pause)
                    MusicService.PlayState.PAUSE ->
                        getBinding().musicPlay.setImageResource(R.drawable.ic_play)
                    MusicService.PlayState.NOPLAY ->
                        getBinding().musicPlay.setImageResource(R.drawable.ic_play)
                }
            }
        }
    }

    private val mMusicReceiver by lazy {
        MusicReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMusicReceiver, IntentFilter(BConstant.ACTION_UPDATE))
        initUI()
        initUIObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver)
    }

    override fun initUIState() {

    }

    override fun initUIObserver() {
        // 更新音乐专辑
        getViewModel().mCurrentMusic.observe(this) {
            Glide.with(this).load(it.albumUrl).into(
                getBinding().musicAlbum
            )
        }
    }

    override fun initUI() {
        val initMusic = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getSerializable(CURRENT_MUSIC_KEY, MainSharedVM.MusicInfo::class.java)
        } else {
            @Suppress("DEPRECATION")
            cast(intent.extras?.getSerializable(CURRENT_MUSIC_KEY))
        }
        initMusic?.apply {
            getViewModel().setCurrentMusic(this)
        }
        val initProgress = intent.getFloatExtra(PROGRESS_KEY, 0f)
        getBinding().musicProgress.setCurrentNum((initProgress * 100).toDouble())
    }

}