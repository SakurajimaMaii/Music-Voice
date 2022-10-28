package cn.govast.gmusic.network.repository

import cn.govast.gmusic.model.music.play.MusicQuality
import cn.govast.gmusic.model.music.play.MusicUrl
import cn.govast.gmusic.model.music.search.MusicSearch
import cn.govast.gmusic.network.ServiceCreator
import cn.govast.gmusic.network.service.MusicService

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

object MusicRepository {

    private val mMusicService by lazy {
        ServiceCreator.create(MusicService::class.java)
    }

    suspend fun searchMusic(name: String): MusicSearch {
        return mMusicService.searchMusic(name)
    }

    /**
     * @see MusicService.getMusicUrl
     */
    suspend fun getMusicUrl(id: Int, quality: MusicQuality): MusicUrl {
        return mMusicService.getMusicUrl(id, quality.value)
    }

}