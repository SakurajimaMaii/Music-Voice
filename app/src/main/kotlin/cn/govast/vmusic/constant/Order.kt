package cn.govast.vmusic.constant


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/**
 * 界面向服务发送的控制信号
 *
 * @see [cn.govast.vmusic.service.MusicService]
 */
enum class Order {
    PLAY_OR_PAUSE,
    STOP,
    NEXT,
    PREVIOUS,
    LOOP,
    RANDOM,
    LOOP_ALL,
    /**
     * 数据索引
     * @see [cn.govast.vmusic.service.MusicService.PROGRESS]
     */
    PROGRESS,
    /**
     * 数据索引
     * @see [cn.govast.vmusic.service.MusicService.NOW]
     */
    NOW,
    /**
     * 数据索引
     * @see [cn.govast.vmusic.service.MusicService.NAME]
     */
    SEARCH
}