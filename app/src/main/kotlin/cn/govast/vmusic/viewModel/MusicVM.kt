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

package cn.govast.vmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cn.govast.vasttools.viewModel.VastViewModel
import cn.govast.vmusic.model.Music
import cn.govast.vmusic.model.MusicPlayWrapper
import cn.govast.vmusic.model.MusicWrapper
import java.io.Serializable

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

class MusicVM : VastViewModel() {

    /**
     * 音乐下载信息
     *
     * @property music
     * @property musicUrl
     */
    data class MusicDownload(val music: Music, val musicUrl: String) : Serializable

    /** 当前播放歌曲 */
    private val _mCurrentMusicPlayWrapper = MutableLiveData<MusicPlayWrapper>()
    val mCurrentMusicPlayWrapper: LiveData<MusicPlayWrapper>
        get() = _mCurrentMusicPlayWrapper

    /** 当前要下载音乐的信息 */
    var mCurrentMusicDownload: MusicDownload =
        MusicDownload(Music("", "", "", "", 0L, "", "", ""), "")

    fun setCurrentMusic(musicWrapper: MusicPlayWrapper) {
        _mCurrentMusicPlayWrapper.postValue(musicWrapper)
        mCurrentMusicDownload = MusicDownload(musicWrapper.music, musicWrapper.musicUrl)
    }


}