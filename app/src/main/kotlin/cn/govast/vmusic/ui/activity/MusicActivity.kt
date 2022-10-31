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

import android.content.*
import android.os.Build
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.vasttools.activity.VastVbVmActivity
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vmusic.R
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.broadcast.MusicBroadcast
import cn.govast.vmusic.constant.Order
import cn.govast.vmusic.constant.UpdateKey
import cn.govast.vmusic.databinding.ActivityMusicBinding
import cn.govast.vmusic.service.musicplay.MusicService
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.ui.base.sendOrderIntent
import cn.govast.vmusic.utils.TimeUtils
import cn.govast.vmusic.viewModel.MusicVM
import com.bumptech.glide.Glide

class MusicActivity : VastVbVmActivity<ActivityMusicBinding, MusicVM>(), UIStateListener {

    companion object {
        /** 接受来自 [MainActivity] 传递的数据 */
        const val CURRENT_MUSIC_NAME_KEY = "current_music_name_key"
        const val CURRENT_MUSIC_ALBUM_KEY = "current_music_album_key"
        const val CURRENT_PROGRESS_KEY = "progress_key"
        const val CURRENT_DURATION_KEY = "current_duration_key"

        /**
         * 确保 musicProgressSlider 起点小于终点
         */
        const val DURATION_OFFSET = 0.0001
    }

    /** 当前播放歌曲的时长 */
    private var mDuration = 0

    private inner class MusicReceiver : MusicBroadcast() {

        override fun onMusicPlay(intent: Intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getSerializable(
                    UpdateKey.MUSIC_PLAY_KEY,
                    MainActivity.MusicInfo::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                cast(intent.extras?.getSerializable(UpdateKey.MUSIC_PLAY_KEY))
            }?.apply {
                getBinding().topAppBar.title = this.name
                Glide.with(getContext()).load(this.albumUrl).into(
                    getBinding().musicAlbum
                )
                getBinding().musicProgressSlider.value = (this.currentProgress * mDuration).toFloat()
            } ?: ToastUtils.showShortMsg("更新失败")
        }

        override fun onProgress(intent: Intent) {
            intent.getFloatExtra(UpdateKey.PROGRESS_KEY, 0f).apply {
                if (!this.isNaN()) {
                    getBinding().musicProgressSlider.also { slider ->
                        slider.value = (this * slider.valueTo)
                    }
                    getBinding().musicDuration.text =
                        String.format(
                            ResUtils.getString(R.string.format_duration),
                            TimeUtils.timeParse((this * mDuration).toLong()),
                            TimeUtils.timeParse(mDuration.toLong())
                        )
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

                    MusicService.PlayState.NOPLAYING ->
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
        initUIState()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver)
    }

    override fun initUIState() {

    }

    override fun initUI() {
        setSupportActionBar(getBinding().topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intent.getStringExtra(CURRENT_MUSIC_NAME_KEY)?.apply {
            getBinding().topAppBar.title = this
        }
        intent.getStringExtra(CURRENT_MUSIC_ALBUM_KEY)?.apply {
            Glide.with(getContext()).load(this).into(
                getBinding().musicAlbum
            )
        }
        val initProgress = intent.getFloatExtra(CURRENT_PROGRESS_KEY, 0f)
        mDuration = intent.getIntExtra(CURRENT_DURATION_KEY, 0).apply {
            if(0 != this){
                getBinding().musicProgressSlider.apply {
                    valueTo = (mDuration + DURATION_OFFSET).toFloat()
                    value = (initProgress * mDuration)
                    setLabelFormatter {
                        TimeUtils.timeParse(it.toLong())
                    }
                }
            }
        }
        getBinding().musicDuration.text =
            String.format(
                ResUtils.getString(R.string.format_duration),
                TimeUtils.timeParse((initProgress * mDuration).toLong()),
                TimeUtils.timeParse(mDuration.toLong())
            )
        getBinding().musicPlayLast.setOnClickListener {
            sendOrderIntent(Order.PREVIOUS)
        }
        getBinding().musicPlay.setOnClickListener {
            sendOrderIntent(Order.PLAY_OR_PAUSE)
        }
        getBinding().musicPlayNext.setOnClickListener {
            sendOrderIntent(Order.NEXT)
        }
    }

}