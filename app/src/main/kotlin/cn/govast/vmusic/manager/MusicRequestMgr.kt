/*
 * Copyright 2022 Vast Gui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.govast.vmusic.manager

import cn.govast.vasttools.base.BaseActive
import cn.govast.vasttools.network.ApiRspStateListener
import cn.govast.vmusic.model.net.music.play.MusicQuality
import cn.govast.vmusic.model.net.music.play.MusicUrl
import cn.govast.vmusic.model.net.music.search.MusicSearch
import cn.govast.vmusic.network.repository.MusicRepository

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/** 主要用于当Service,Activity接收到指令时候的数据更新 */
object MusicRequestMgr : BaseActive {

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
     * @param id
     * @param quality
     * @param listener
     */
    fun getMusicUrl(
        id: Int,
        quality: MusicQuality,
        listener: ApiRspStateListener<MusicUrl>.() -> Unit
    ) {
        getApiRequestBuilder()
            .suspendWithListener({ MusicRepository.getMusicUrl(id, quality) }, listener)
    }

    override fun getDefaultTag(): String {
        return this.javaClass.simpleName
    }
}