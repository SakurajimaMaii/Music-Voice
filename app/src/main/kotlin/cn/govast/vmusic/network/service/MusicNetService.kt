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

package cn.govast.vmusic.network.service

import cn.govast.vmusic.model.net.music.play.MusicUrl
import cn.govast.vmusic.model.net.music.search.MusicSearch
import cn.govast.vmusic.model.net.toplist.TopList
import retrofit2.http.POST
import retrofit2.http.Query

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

interface MusicNetService {
    /**
     * [根据name搜索歌曲](https://neteasecloudmusicapi.vercel.app/#/?id=%e6%90%9c%e7%b4%a2)
     *
     * @param name
     * @return
     */
    @POST("/cloudsearch")
    suspend fun searchMusic(@Query("keywords") name: String): MusicSearch

    /**
     * [获取音乐Url](https://neteasecloudmusicapi.vercel.app/#/?id=%e8%8e%b7%e5%8f%96%e9%9f%b3%e4%b9%90-url-%e6%96%b0%e7%89%88)
     *
     * @param id 音乐id
     * @param level 播放等级
     */
    @POST("/song/url/v1")
    suspend fun getMusicUrl(@Query("id") id: Int, @Query("level") level: String): MusicUrl

    /**
     * [获取所有榜单](https://neteasecloudmusicapi.vercel.app/#/?id=%e6%89%80%e6%9c%89%e6%a6%9c%e5%8d%95)
     *
     * @return
     */
    @POST("/toplist")
    suspend fun getTopList(): TopList
}