package com.gcode.gmusic

import com.gcode.gmusic.model.MusicBean
import com.gcode.tools.adapter.BaseUtilItem
import java.util.*

object SearchSong {
    fun searchSongByName(
        musicBeans: List<BaseUtilItem>,
        searchName: CharSequence
    ): List<BaseUtilItem> {
        val searchResult: MutableList<BaseUtilItem> = ArrayList()
        for (bean in musicBeans) {
            val item = bean as MusicBean
            if (item.song.contains(searchName)) {
                searchResult.add(bean)
            }
        }
        return searchResult
    }
}