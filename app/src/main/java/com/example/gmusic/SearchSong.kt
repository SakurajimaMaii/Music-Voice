package com.example.gmusic

import java.util.*

object SearchSong {
    fun searchSongByName(
        musicBeans: List<BindingAdapterItem>,
        searchName: CharSequence
    ): List<BindingAdapterItem> {
        val searchResult: MutableList<BindingAdapterItem> = ArrayList()
        for (bean in musicBeans) {
            val item = bean as LocalMusicBean
            if (item.song.contains(searchName)) {
                searchResult.add(bean)
            }
        }
        return searchResult
    }
}