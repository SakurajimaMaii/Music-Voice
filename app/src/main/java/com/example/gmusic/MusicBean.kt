package com.example.gmusic

import android.graphics.Bitmap
import com.gcode.gutils.adapter.BaseItem

data class MusicBean(
    val id: String,
    var song: String, //歌手名称
    var singer: String?, //专辑名称
    var album: String?, //歌曲时长
    var duration: Long?, //歌曲路径
    var path: String, //专辑地址
    var albumArt: Bitmap
):BaseItem {
    override fun getItemBindViewType(): Int {
        return R.layout.local_music_recycle_item
    }

    override fun getItemViewType(): Int {
        return 0
    }
}