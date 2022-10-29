package cn.govast.gmusic.manager

import cn.govast.gmusic.model.music.play.MusicQuality
import cn.govast.gmusic.model.music.play.MusicUrl
import cn.govast.gmusic.model.music.search.MusicSearch
import cn.govast.gmusic.model.music.search.Song
import cn.govast.gmusic.network.repository.MusicRepository
import cn.govast.vasttools.base.BaseActive
import cn.govast.vasttools.network.ApiRspStateListener

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/** 主要用于当Service,Activity接收到指令时候的数据更新 */
object MusicMgr : BaseActive {

    /**
     * 搜索歌曲
     *
     * @param name
     * @param listener
     */
    fun searchMusic(name: String, listener: ApiRspStateListener<MusicSearch>.() -> Unit) {
        getApiRequestBuilder()
            .suspendWithListener({ MusicRepository.searchMusic(name) }, listener)
    }

    /**
     * 获取歌曲Url
     *
     * @param song
     * @param quality
     * @param listener
     */
    fun getMusicUrl(
        song: Song,
        quality: MusicQuality,
        listener: ApiRspStateListener<MusicUrl>.() -> Unit
    ) {
        getApiRequestBuilder()
            .suspendWithListener({ MusicRepository.getMusicUrl(song.id, quality) }, listener)
    }

    override fun getDefaultTag(): String {
        return this.javaClass.simpleName
    }
}