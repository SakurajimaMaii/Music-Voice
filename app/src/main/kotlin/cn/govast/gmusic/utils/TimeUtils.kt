package cn.govast.gmusic.utils

import kotlin.math.roundToInt


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

object TimeUtils {

    /**
     * Android 音乐播放器应用里，读出的音乐时长为 long 类型以毫秒数为单位，例如：将 234736 转化为分钟和秒应为 03:55 （包含四舍五入）
     * @param duration 音乐时长
     * @return
     */
    fun timeParse(duration: Long): String? {
        var time: String? = ""
        val minute = duration / 60000
        val seconds = duration % 60000
        val second = (seconds.toFloat() / 1000).roundToInt().toLong()
        if (minute < 10) {
            time += "0"
        }
        time += "$minute:"
        if (second < 10) {
            time += "0"
        }
        time += second
        return time
    }

}