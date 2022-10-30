package cn.govast.vmusic.model.music.play

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

/**
 * [播放音质](https://neteasecloudmusicapi.vercel.app/#/?id=%e8%8e%b7%e5%8f%96%e9%9f%b3%e4%b9%90-url-%e6%96%b0%e7%89%88)
 *
 * @property value
 */
enum class MusicQuality(val value:String) {
    STANDARD("standard"), HIGHER("higher"), EXHIGH("exhigh"), LOSSLESS("lossless"), HIRES("hires")
}