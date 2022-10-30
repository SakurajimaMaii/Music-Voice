package cn.govast.vmusic.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import cn.govast.vmusic.constant.Update
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.nothing_to_do
import cn.govast.vasttools.utils.ToastUtils

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/**
 * 用于通知页面更新音乐信息
 *
 * @property onMusicInitLoaded 用于通知页面获取默认数据，只会在初次加载时被监听
 * @property onMusicLoaded 用于通知页面获取数据
 * @property onMusicPlay 用于通知页面现在播放的歌曲
 * @property onProgress 用于通知页面现在的进度
 * @property onPlayState 用于通知页面当前的播放状态
 */
interface StateListener{
    fun onMusicInitLoaded(intent: Intent)
    fun onMusicLoaded(intent: Intent)
    fun onMusicPlay(intent: Intent)
    fun onProgress(intent: Intent)
    fun onPlayState(intent: Intent)
}

/** 用于活动页面监听 [cn.govast.vmusic.service.MusicService] 内发送的广播 */
abstract class MusicBroadcast: BroadcastReceiver(),StateListener {
    override fun onReceive(context: Context, intent: Intent) {
        val update = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getSerializable(BConstant.UPDATE_KEY, Update::class.java)
        } else {
            @Suppress("DEPRECATION")
            (cast(intent.extras?.getSerializable(BConstant.UPDATE_KEY)))
        }
        update?.also {
            when (it) {
                Update.ON_MUSIC_INIT_LOADED -> {
                    onMusicInitLoaded(intent)
                }
                Update.ON_MUSIC_LOADED -> {
                    onMusicLoaded(intent)
                }
                Update.ON_MUSIC_PLAY -> {
                    onMusicPlay(intent)
                }
                Update.ON_PROGRESS -> {
                    onProgress(intent)
                }
                Update.ON_PLAY_STATE -> {
                    onPlayState(intent)
                }
            }
        } ?: ToastUtils.showShortMsg("未正确获取更新指令")
    }

    override fun onMusicInitLoaded(intent: Intent) {
        nothing_to_do()
    }

    override fun onMusicLoaded(intent: Intent) {
        nothing_to_do()
    }

    override fun onMusicPlay(intent: Intent) {
        nothing_to_do()
    }

    override fun onProgress(intent: Intent) {
        nothing_to_do()
    }

    override fun onPlayState(intent: Intent) {
        nothing_to_do()
    }
}