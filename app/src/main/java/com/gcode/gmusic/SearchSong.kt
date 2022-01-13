package com.gcode.gmusic

import com.gcode.gmusic.model.MusicBean
import com.gcode.tools.adapter.BaseGcodeItem
import java.util.*

object SearchSong {
    fun searchSongByName(
        musicBeans: List<BaseGcodeItem>,
        searchName: CharSequence
    ): List<BaseGcodeItem> {
        val searchResult: MutableList<BaseGcodeItem> = ArrayList()
        for (bean in musicBeans) {
            val item = bean as MusicBean
            if (item.song.contains(searchName)) {
                searchResult.add(bean)
            }
        }
        return searchResult
    }
}