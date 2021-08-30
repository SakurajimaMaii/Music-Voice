package com.gcode.gmusic

import com.gcode.gmusic.model.MusicBean
import com.gcode.tools.adapter.BaseItem
import java.util.*

object SearchSong {
    fun searchSongByName(
        musicBeans: List<BaseItem>,
        searchName: CharSequence
    ): List<BaseItem> {
        val searchResult: MutableList<BaseItem> = ArrayList()
        for (bean in musicBeans) {
            val item = bean as MusicBean
            if (item.song.contains(searchName)) {
                searchResult.add(bean)
            }
        }
        return searchResult
    }
}