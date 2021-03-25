package com.example.gmusic

import android.graphics.Bitmap

data class LocalMusicBean(
    val id: String,
    var song: String, //歌手名称
    var singer: String, //专辑名称
    var album: String, //歌曲时长
    var duration: String, //歌曲路径
    var path: String, //专辑地址
    var albumArt: Bitmap
):BindingAdapterItem {
    /**
     * 返回布局id
     * @return int
     */
    override fun getViewType(): Int {
        return R.layout.item_local_music
    }
}