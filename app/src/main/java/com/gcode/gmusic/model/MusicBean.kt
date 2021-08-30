package com.gcode.gmusic.model

import com.example.gmusic.R
import com.gcode.tools.adapter.BaseItem

data class MusicBean(
    val id:Int,
    var song: String, //歌手名称
    var singer: String?, //专辑名称
    var album: String?, //歌曲时长
    var duration: Long?, //歌曲路径
): BaseItem {
    override fun getItemBindViewType(): Int {
        return R.layout.rv_item_local_music
    }

    override fun getItemViewType(): Int {
        return 0
    }
}