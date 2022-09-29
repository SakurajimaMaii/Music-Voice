package cn.govast.gmusic

import cn.govast.gmusic.model.MusicBean
import cn.govast.vasttools.extension.cast
import com.gcode.vastadapter.interfaces.VastBindAdapterItem
import java.util.*

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 14:48
// Description:
// Documentation:

object SearchSong {
    fun searchSongByName(
        musicBeans: List<VastBindAdapterItem>,
        searchName: CharSequence
    ): List<VastBindAdapterItem> {
        val searchResult: MutableList<VastBindAdapterItem> = ArrayList()
        for (bean in musicBeans) {
            val item = cast<MusicBean>(bean)
            if (item.song.contains(searchName)) {
                searchResult.add(bean)
            }
        }
        return searchResult
    }
}