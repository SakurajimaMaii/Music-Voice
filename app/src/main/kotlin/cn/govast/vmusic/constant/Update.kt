package cn.govast.vmusic.constant

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/**
 * [cn.govast.vmusic.service.MusicService] 更新广播通知
 *
 * @property ON_MUSIC_INIT_LOADED 这条指令尽量不要由用户发送，这个专门用来初次启动应用更新的
 */
enum class Update {
    ON_MUSIC_INIT_LOADED,
    ON_MUSIC_LOADED,
    ON_MUSIC_PLAY,
    ON_PROGRESS,
    ON_PLAY_STATE
}