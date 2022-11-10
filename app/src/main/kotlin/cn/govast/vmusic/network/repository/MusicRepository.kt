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

package cn.govast.vmusic.network.repository

import cn.govast.vmusic.model.net.music.play.MusicQuality
import cn.govast.vmusic.model.net.music.play.MusicUrl
import cn.govast.vmusic.model.net.music.search.MusicSearch
import cn.govast.vmusic.model.net.toplist.TopList
import cn.govast.vmusic.network.ServiceCreator
import cn.govast.vmusic.network.service.MusicNetService

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

    suspend fun getTopList(): TopList {
        return mMusicNetService.getTopList()
    }

}