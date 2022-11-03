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
import cn.govast.vmusic.model.net.music.search.MusicSearch
import cn.govast.vmusic.utils.TimeUtils
import java.io.Serializable

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/1
// Description: 
// Documentation:
// Reference:

/**
 * 下载到本地的歌曲
 *
 * @property name 歌曲名称,索引键 [android.provider.MediaStore.Audio.Media.TITLE]
 * @property artist 歌手名,索引键
 *     [android.provider.MediaStore.Audio.Media.ARTIST]
 * @property album 专辑名称,索引键 [android.provider.MediaStore.Audio.Media.ALBUM]
 * @property path 歌曲文件路径,索引键 [android.provider.MediaStore.Audio.Media.DATA]
 * @property duration 播放时长,索引键
 *     [android.provider.MediaStore.Audio.Media.DURATION]
 * @property fileName 歌曲文件名,索引键
 *     [android.provider.MediaStore.Audio.Media.DISPLAY_NAME]
 * @property year 歌曲发行日期,索引键 [android.provider.MediaStore.Audio.Media.YEAR]
 * @property albumArt 歌曲专辑封面
 *     [android.provider.MediaStore.Audio.Media.ALBUM_ARTIST]
 */
data class Music(
    val name: String,
    val artist: String,
    val album: String,
    var path: String,
    val duration: Long,
    var fileName: String,
    val year: String,
    val albumArt: String
) : Serializable {

    companion object {
        const val DEFAULT_CONTENT = ""
    }

    /** 构造函数 */
    constructor(song: MusicSearch.Content.Song) :
            this(
                song.name,
                song.artist[0].name,
                song.album.name,
                DEFAULT_CONTENT,
                song.duration.toLong(),
                DEFAULT_CONTENT,
                DEFAULT_CONTENT,
                song.album.getPicUrl()
            )

    /** 返回时长字符串 */
    fun getDurationStr() = TimeUtils.timeParse(duration.toLong())

}