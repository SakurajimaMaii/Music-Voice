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

package cn.govast.vmusic.model

import cn.govast.vastadapter.AdapterItem
import cn.govast.vmusic.R
import cn.govast.vmusic.utils.TimeUtils

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/1
// Description: 
// Documentation:
// Reference:

/**
 * 下载到本地的歌曲
 *
 * @property name 歌曲名
 * @property singer 歌手名
 * @property album 专辑名称
 * @property duration 播放时长
 * @property path
 * @property albumArt
 */
data class LocalMusic(
    val name:String,
    var singer: String,
    var album: String,
    val duration: Long,
    var path: String
):AdapterItem {

    /** 返回时长字符串 */
    fun getDurationStr() = TimeUtils.timeParse(duration.toLong())

    /**
     * 返回布局id
     * @return int
     */
    override fun getBindType(): Int {
        return R.layout.rv_item_local_music
    }
}