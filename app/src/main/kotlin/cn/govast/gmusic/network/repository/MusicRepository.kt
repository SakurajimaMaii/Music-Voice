package cn.govast.gmusic.network.repository

import cn.govast.gmusic.model.music.play.MusicQuality
import cn.govast.gmusic.model.music.play.MusicUrl
import cn.govast.gmusic.model.music.search.MusicSearch
import cn.govast.gmusic.network.ServiceCreator
import cn.govast.gmusic.network.service.MusicNetService

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

object MusicRepository {

    private val mMusicNetService by lazy {
        ServiceCreator.create(MusicNetService::class.java)
    }

    suspend fun searchMusic(name: String): MusicSearch {
        return mMusicNetService.searchMusic(name)
    }

    /**
     * @see MusicNetService.getMusicUrl
     */
    suspend fun getMusicUrl(id: Int, quality: MusicQuality): MusicUrl {
        return mMusicNetService.getMusicUrl(id, quality.value)
    }

}