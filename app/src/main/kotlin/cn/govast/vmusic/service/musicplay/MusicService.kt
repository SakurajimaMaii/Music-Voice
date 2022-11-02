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

package cn.govast.vmusic.service.musicplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.music.MusicPlayer
import cn.govast.vasttools.service.VastService
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.constant.Order
import cn.govast.vmusic.constant.Update
import cn.govast.vmusic.constant.UpdateKey
import cn.govast.vmusic.constant.UpdateKey.DURATION_KEY
import cn.govast.vmusic.constant.UpdateKey.PLAY_STATE_KEY
import cn.govast.vmusic.constant.UpdateKey.PROGRESS_KEY
import cn.govast.vmusic.manager.MusicRequestMgr
import cn.govast.vmusic.manager.CurrentPlayMusicMgr
import cn.govast.vmusic.model.net.music.play.MusicQuality
import cn.govast.vmusic.model.net.music.search.Song
import cn.govast.vmusic.ui.activity.MainActivity
import cn.govast.vmusic.ui.base.sendUpdateIntent


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference: https://blog.csdn.net/qq_45744856/article/details/121897063

class MusicService : VastService() {

    companion object {
        /** 默认加载歌曲页面 */
        const val DEFAULT_MUSIC_NAME = "海阔天空"

        /** 传递当前播放歌曲的index */
        const val NOW = "now"

        /** 页面传递当前播放进度 */
        const val PROGRESS = "progress"

        /** 查询歌名索引 */
        const val NAME = "name"
    }

    /** 播放状态 */
    enum class PlayState {
        NOPLAYING, PLAYING, PAUSE
    }

    /**
     * 循环播放方式
     *
     * @property LIST 列表顺序播放
     * @property ONE 单曲循环
     * @property RANDOM 随机播放
     */
    enum class LoopMode {
        LIST, ONE, RANDOM
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    /**
     * 当前播放状态
     *
     * @see PlayState
     */
    private var mCurrentPlayStatus = PlayState.NOPLAYING

    /** 当前待播放音乐列表 */
    private val mCurrentPlayList: MutableList<Song> = ArrayList()

    /**
     * 当前歌曲循环模式
     *
     * @see LoopMode
     */
    private var mCurrentLoopMode: LoopMode = LoopMode.LIST

    /** 记录当前正在播放的音乐 */
    private var current = 0

    /** 用于接收广播 */
    private lateinit var serviceReceiver: ServiceReceiver

    // 线程运行状态标记位 true表示正在运行
    private var isRunning = false

    /** 用来通知页面进程更新 */
    private var thread: Thread? = null

    /** 用于播放音乐 */
    private val mMusicPlayer by lazy {
        MusicPlayer(this)
    }

    /**
     * 用来管理音乐索引
     *
     * 每次执行搜索操作后进行更新
     */
    private val mCurrentPlayMusicMgr by lazy {
        CurrentPlayMusicMgr()
    }

    /** 用于和 Activity 进行数据交互 */
    private var mMusicServiceDataListener: MusicServiceDataListener? = null

    override fun onBind(intent: Intent): IBinder {
        return MusicBinder()
    }

    override fun onCreate() {
        // 创建BroadcastReceiver
        serviceReceiver = ServiceReceiver()
        // 创建IntentFilter，MainActivity.CONTROL代表接收信息来源
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(serviceReceiver, IntentFilter(BConstant.ACTION_CONTROL))
        // 加载默认数据
        searchMusic(DEFAULT_MUSIC_NAME, true)
        // 为MediaPlayer播放完成后
        // 根据当前循环状态播放下一首
        mMusicPlayer.mediaPlayer.setOnCompletionListener {
            mCurrentPlayStatus = PlayState.PLAYING
            val music = mCurrentPlayMusicMgr.getIndexAfterUpdateLoop(mCurrentLoopMode)
            playMusic(music, MusicQuality.EXHIGH)
        }
        super.onCreate()
        // 设置线程循环控制变量为真
        isRunning = true
        // 创建线程更新播放进度
        // 判断音乐是否在播放
        // 创建意图 ：更新播放进度
        // 让意图携带当前播放时点
        // 按意图发送广播
        // 让线程睡眠500毫秒
        thread = Thread {
            while (isRunning) {
                // 判断音乐是否在播放
                if (mMusicPlayer.mediaPlayer.isPlaying) {
                    sendUpdateIntent(Update.ON_PROGRESS) { intent ->
                        intent.putExtra(DURATION_KEY, mMusicPlayer.getDuration())
                        intent.putExtra(PROGRESS_KEY, mMusicPlayer.getProgressPercent())
                    }
                }
                // 让线程睡眠500毫秒
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.apply { start() }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 后台重启时更新页面，恢复到退出之前的状态
        mMusicServiceDataListener?.updateCurrentMusicList?.invoke(
            mCurrentPlayList,
            mCurrentPlayStatus
        )
        return super.onStartCommand(intent, flags, startId)
    }

    private inner class ServiceReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val control = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getSerializable(BConstant.CONTROL_KEY, Order::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.extras?.getSerializable(BConstant.CONTROL_KEY)
            }
            when (control) {
                Order.PLAY_OR_PAUSE -> // 原来处于没有播放状态
                    when (mCurrentPlayStatus) {
                        PlayState.NOPLAYING -> {
                            mCurrentPlayStatus = PlayState.PLAYING
                            playMusic(mCurrentPlayMusicMgr.getCurrentSong(), MusicQuality.EXHIGH)
                        }

                        PlayState.PLAYING -> {
                            mCurrentPlayStatus = PlayState.PAUSE
                            mMusicPlayer.pauseMusic()
                        }

                        PlayState.PAUSE -> {
                            mCurrentPlayStatus = PlayState.PLAYING
                            mMusicPlayer.playMusic()
                        }
                    }

                Order.STOP -> // 如果原来正在播放或暂停
                    if (mCurrentPlayStatus == PlayState.PLAYING || mCurrentPlayStatus == PlayState.PAUSE) {
                        mCurrentPlayMusicMgr.resetIndex()
                        mMusicPlayer.resetMusic()
                        mCurrentPlayStatus = PlayState.NOPLAYING
                    }

                Order.NEXT -> {
                    mCurrentPlayStatus = PlayState.PLAYING
                    playMusic(mCurrentPlayMusicMgr.getNextSong(), MusicQuality.EXHIGH)
                }

                Order.PREVIOUS -> {
                    mCurrentPlayStatus = PlayState.PLAYING
                    playMusic(mCurrentPlayMusicMgr.getPreviewSong(), MusicQuality.EXHIGH)
                }

                Order.NOW -> {
                    mCurrentPlayStatus = PlayState.PLAYING
                    intent.getIntExtra(NOW, 0).apply {
                        playMusic(mCurrentPlayMusicMgr.getSongByIndex(this), MusicQuality.EXHIGH)
                    }
                }

                Order.LOOP_LIST -> {
                    mCurrentLoopMode = LoopMode.LIST
                }

                Order.LOOP_ONE -> {
                    mCurrentLoopMode = LoopMode.ONE
                }

                Order.LOOP_RANDOM -> {
                    mCurrentLoopMode = LoopMode.RANDOM
                }

                Order.SEARCH -> {
                    intent.getStringExtra(NAME)?.also {
                        searchMusic(it)
                        LogUtils.d(getDefaultTag(), it)
                    } ?: ToastUtils.showShortMsg("未正确接收到查询的歌名")
                }
            }
            // 通知界面更新状态
            sendUpdateIntent(Update.ON_PLAY_STATE) { mIntent ->
                val bundle = Bundle().also { bundle ->
                    bundle.putSerializable(UpdateKey.PLAY_STATE_KEY, mCurrentPlayStatus)
                }
                mIntent.putExtras(bundle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 解除注册
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceReceiver)
        // 释放媒体播放器
        mMusicPlayer.mediaPlayer.let {
            it.stop()
            it.release()
            null
        }
        // 设置线程循环控制变量
        isRunning = false
        // 销毁子线程
        thread = null
    }

    /**
     * 获取音乐Url并播放
     *
     * @param song 音乐
     * @param quality 音乐播放质量
     */
    @Suppress("SameParameterValue")
    private fun playMusic(song: Song, quality: MusicQuality) {
        MusicRequestMgr.getMusicUrl(song, quality) {
            onSuccess = {
                mMusicPlayer.playMusic(it.data[0].getUrl())
                mMusicServiceDataListener?.updateCurrentMusic?.invoke(song, it.data[0].getUrl())
                // 通知页面更新
                sendUpdateIntent(Update.ON_MUSIC_PLAY) {
                    val bundle = Bundle().also {
                        it.putSerializable(
                            UpdateKey.MUSIC_PLAY_KEY,
                            MainActivity.MusicInfo(
                                song.name,
                                song.album.getPicUrl(),
                                mMusicPlayer.getProgressPercent().toInt(),
                                song.duration
                            )
                        )
                    }
                    it.putExtras(bundle)
                }
            }
        }
    }

    /**
     * 搜索歌曲
     *
     * @param isInit 是否用来初次初始化数据列表,默认false
     */
    @JvmOverloads
    fun searchMusic(name: String, init: Boolean = false) {
        MusicRequestMgr.searchMusic(name) {
            onSuccess = {
                mCurrentPlayList.apply {
                    clear()
                    addAll(it.result.songs)
                }
                mMusicServiceDataListener?.updateCurrentMusicList?.invoke(
                    mCurrentPlayList,
                    mCurrentPlayStatus
                )
                if (init) {
                    mCurrentPlayMusicMgr.initMgr(it.result.songs)
                } else {
                    mCurrentPlayMusicMgr.resetMgr(it.result.songs)
                }
            }
            onError = {
                LogUtils.e(getDefaultTag(), it?.message ?: "歌曲搜索失败")
            }
        }
    }

    /**
     * 注册一个数据加载完成监听
     *
     * @param listener
     */
    fun registerMusicListener(listener: MusicServiceDataListener.() -> Unit) {
        mMusicServiceDataListener = MusicServiceDataListener().also(listener)
    }

}