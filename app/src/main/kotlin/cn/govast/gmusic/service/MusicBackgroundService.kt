package cn.govast.gmusic.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.gmusic.model.music.play.MusicQuality
import cn.govast.gmusic.model.music.search.Song
import cn.govast.gmusic.network.repository.MusicRepository
import cn.govast.gmusic.ui.activity.MainActivity
import cn.govast.gmusic.ui.base.Order
import cn.govast.music.MusicPlayer
import cn.govast.vasttools.service.VastService
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.utils.ToastUtils


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference: https://blog.csdn.net/qq_45744856/article/details/121897063

class MusicBackgroundService : VastService() {

    companion object {
        /** 默认加载歌曲页面 */
        private const val DEFAULT_MUSIC_NAME = "海阔天空"

        /** 传递当前播放歌曲的index */
        const val NOW = "now"

        /** 界面发送控制信号 */
        const val CONTROL = "control"

        /** 页面传递当前播放进度 */
        const val PROGRESS = "progress"

        /**
         * 查询歌名索引
         */
        const val NAME = "name"
    }

    /** 播放状态 */
    enum class PlayState {
        NOPLAY, PLAYING, PAUSE
    }

    /** 循环播放方式 */
    enum class Loop {
        LIST, // 列表顺序播放
        ONE, // 单曲循环
        RANDOM // 随机播放
    }

    inner class MusicBinder : Binder() {
        val mService: MusicBackgroundService
            get() = this@MusicBackgroundService
    }


    /** 当前状态 */
    private var status = PlayState.NOPLAY

    /** 带播放音乐列表 */
    private val songList:MutableList<Song> = ArrayList()

    /**
     * 歌曲循环模式
     */
    private var loopType: Loop = Loop.LIST

    /** 记录当前正在播放的音乐 */
    private var current = 0

    // 广播接收器
    private lateinit var serviceReceiver: ServiceReceiver

    // 线程运行状态标记位 true表示正在运行
    private var isRunning = false

    // 线程
    private var thread: Thread? = null

    private val mMusicPlayer by lazy {
        MusicPlayer(this)
    }

    /**
     * 用于通知页面更新音乐信息
     *
     * @property onMusicLoaded 用于通知页面获取默认数据
     * @property onMusicPlay 用于通知页面现在播放的歌曲
     * @property onProgress 用于通知页面现在的进度
     * @property onPlayState 用于通知页面当前的播放状态
     */
    class MusicListener {
        var onMusicLoaded: ((songs: List<Song>) -> Unit) = {}
        var onMusicPlay: ((song: Song) -> Unit) = {}
        var onProgress: ((progress: Float) -> Unit) = {}
        var onPlayState: ((play:PlayState)->Unit) = {}
    }

    /** @see MusicListener */
    private var musicListener: MusicListener? = null

    override fun onBind(intent: Intent): IBinder {
        return MusicBinder()
    }

    override fun onCreate() {
        // 创建BroadcastReceiver
        serviceReceiver = ServiceReceiver()
        // 创建IntentFilter，MainActivity.CONTROL代表接收信息来源
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceReceiver, IntentFilter(MainActivity.ACTION_CONTROL))
        // 加载默认数据
        initMusicList()
        // 更新状态
        status = PlayState.PLAYING
        // 为MediaPlayer播放完成事件绑定监听器
        mMusicPlayer.mediaPlayer.setOnCompletionListener {
            // 完成播放查看当前的循环标记位
            // 列表循环直接下一首
            // 单曲循环则歌曲标记current不需要做更新操作，下一次直接继续播放之前的音乐
            // 随机播放直接在[0,info.size()-1]范围内随机数
            when (loopType) {
                Loop.LIST -> setNextMusic()
                Loop.ONE -> {}
                Loop.RANDOM -> {
                    val max = songList.size - 1
                    val min = 0
                    val randomNum = System.currentTimeMillis()
                    current = (randomNum % (max - min) + min).toInt()
                }
            }
            status = PlayState.PLAYING
            musicListener?.onMusicPlay?.invoke(songList[current])
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
                    // 创建意图 ：更新播放进度
                    musicListener?.onProgress?.invoke(mMusicPlayer.getProgressPercent())
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
        status = PlayState.PLAYING
        musicListener?.onMusicPlay?.invoke(songList[current])
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 加载默认音乐播放列表
     *
     * @see DEFAULT_MUSIC_NAME
     */
    private fun initMusicList() {
        getRequestBuilder()
            .suspendWithListener({ MusicRepository.searchMusic(DEFAULT_MUSIC_NAME) }) {
                onSuccess = {
                    songList.clear()
                    it.result.songs.forEach { music->
                        songList.add(music)
                    }
                    musicListener?.onMusicLoaded?.invoke(songList)
                }
            }
    }

    // 下一首更新标记即可，溢出清零
    private fun setNextMusic() {
        current++
        if (current >= songList.size) {
            current = 0
        }
    }

    // 上一首更新标记即可，越界设置最后一首
    private fun setPreviousMusic() {
        current--
        if (current < 0) {
            current = songList.size - 1
        }
    }

    inner class ServiceReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // 更新音乐 control代表更新播放音乐，update代表更新图片以及音乐信息
            val control = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getSerializable(CONTROL, Order::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.extras?.getSerializable(CONTROL)
            }
            LogUtils.d(getDefaultTag(),control.toString())
            // 获取当前音乐
            val music = songList[current]
            // 标记当前是更新循环格式还是更新状态 false代表循环格式
            var flag = true
            when (control) {
                Order.PLAY_OR_PAUSE -> // 原来处于没有播放状态
                    when (status) {
                        PlayState.NOPLAY -> {
                            status = PlayState.PLAYING
                            getMusicUrl(music, MusicQuality.EXHIGH)
                        }
                        PlayState.PLAYING -> {
                            status = PlayState.PAUSE
                            mMusicPlayer.pauseMusic()
                        }
                        PlayState.PAUSE -> {
                            status = PlayState.PLAYING
                            mMusicPlayer.playMusic()
                        }
                    }
                Order.STOP ->
                    if (status == PlayState.PLAYING || status == PlayState.PAUSE) {  // 如果原来正在播放或暂停
                        // 标记位清零
                        current = 0
                        // 重置歌曲
                        mMusicPlayer.resetMusic()
                        // 状态更改为未播放
                        status = PlayState.NOPLAY
                    }
                Order.NEXT -> {
                    // 更新 current
                    setNextMusic()
                    // 更新状态
                    status = PlayState.PLAYING
                    // 获取下一首歌曲的信息
                    // 准备播放下一首
                    getMusicUrl(songList[current], MusicQuality.EXHIGH)
                }
                Order.PREVIOUS -> {
                    // 更新 current
                    setPreviousMusic()
                    // 更新状态
                    status = PlayState.PLAYING
                    // 获取上一首歌曲的信息
                    getMusicUrl(songList[current], MusicQuality.EXHIGH)
                }
                Order.PROGRESS -> {
                    // 状态转化为播放
                    status = PlayState.PLAYING
                    // 获取进度百分比 (整数)
                    val progress = intent.getFloatExtra(PROGRESS, 0f)
                    mMusicPlayer.seekTo((progress * mMusicPlayer.getDuration()).toInt())
                }
                Order.NOW -> {
                    // 状态转化为播放
                    status = PlayState.PLAYING
                    // 获取被点击歌曲的序号
                    val x = intent.getIntExtra(NOW, 0)
                    // 更新歌曲
                    getMusicUrl(songList[x], MusicQuality.EXHIGH)
                    // 更新current
                    current = x
                }
                Order.LOOP_ALL -> {
                    flag = false
                    loopType = Loop.LIST
                }
                Order.LOOP -> {
                    flag = false
                    loopType = Loop.ONE
                }
                Order.RANDOM -> {
                    flag = false
                    loopType = Loop.RANDOM
                }
                Order.SEARCH -> {
                    intent.getStringExtra(NAME)?.also {
                        searchMusic(it)
                        LogUtils.d(getDefaultTag(),it)
                    } ?: ToastUtils.showShortMsg("未正确接收到查询的歌名")
                }
            }
            musicListener?.onPlayState?.invoke(status)
            // 当前是更新信息并非更新循环样式，循环样式要等到歌曲结束才更新信息
            if (flag) {
                musicListener?.onMusicPlay?.invoke(music)
                if (status == PlayState.PLAYING || status == PlayState.PAUSE) {
                    musicListener?.onProgress?.invoke(mMusicPlayer.getProgressPercent())
                }
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
     * 注册一个数据加载完成监听
     *
     * @param listener
     */
    fun registerMusicListener(listener: MusicListener.() -> Unit) {
        musicListener = MusicListener().also(listener)
    }

    /**
     * 获取音乐Url并播放
     *
     * @param song 音乐
     * @param quality 音乐播放质量
     */
    private fun getMusicUrl(song: Song, quality: MusicQuality) {
        getRequestBuilder()
            .suspendWithListener({ MusicRepository.getMusicUrl(song.id, quality) }) {
                onSuccess = {
                    mMusicPlayer.playMusic(it.data[0].getUrl())
                }
            }
    }

    /**
     * 搜索歌曲
     *
     * @param name
     */
    fun searchMusic(name: String) {
        getRequestBuilder()
            .suspendWithListener({ MusicRepository.searchMusic(name) }) {
                onSuccess = {
                    songList.clear()
                    for (song in it.result.songs) {
                        songList.add(song)
                    }
                    musicListener?.onMusicLoaded?.invoke(songList)
                }
                onError = {
                    LogUtils.e(getDefaultTag(), it?.message ?: "歌曲搜索失败")
                }
            }
    }


}