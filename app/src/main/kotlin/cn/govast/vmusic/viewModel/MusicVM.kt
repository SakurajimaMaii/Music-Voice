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
import cn.govast.vmusic.model.music.search.Song
import cn.govast.vasttools.viewModel.VastViewModel

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

class MusicVM : VastViewModel() {

    /** 当前页面的Music */
    private val _mCurrentMusic = MutableLiveData<MainSharedVM.MusicInfo>()

    val mCurrentMusic: LiveData<MainSharedVM.MusicInfo>
        get() = _mCurrentMusic

    /** 设置 [cn.govast.vmusic.databinding.ActivityMusicBinding] 页面的音乐信息 */
    fun setCurrentMusic(song: Song) {
        _mCurrentMusic.postValue(
            MainSharedVM.MusicInfo(
                song.name,
                song.album.name,
                song.album.getPicUrl()
            )
        )
    }

    /** 设置 [cn.govast.vmusic.databinding.ActivityMusicBinding] 页面的音乐信息 */
    fun setCurrentMusic(musicInfo: MainSharedVM.MusicInfo) {
        _mCurrentMusic.postValue(musicInfo)
    }
}