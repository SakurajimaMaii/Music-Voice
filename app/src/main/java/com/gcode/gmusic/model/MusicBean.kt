package com.gcode.gmusic.model

import com.example.gmusic.R
import com.gcode.tools.adapter.BaseUtilItem

data class MusicBean(
    val id:Int,
    var song: String, //歌手名称
    var singer: String?, //专辑名称
    var album: String?, //歌曲时长
    var duration: Long?, //歌曲路径
): BaseUtilItem {
    override fun getItemBindViewType(): Int {
        return R.layout.rv_item_local_music
    }

    override fun getItemViewType(): Int {
        return 0
    }
}