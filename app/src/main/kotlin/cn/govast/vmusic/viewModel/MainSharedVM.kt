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
import cn.govast.vasttools.viewModel.VastViewModel
import cn.govast.vmusic.model.net.music.search.Song
import cn.govast.vmusic.service.musicplay.MusicService
import java.io.Serializable

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 14:48
// Description:
// Documentation:

class MainSharedVM : VastViewModel() {

    enum class ProgressState {
        HIDE
    }

    /** 当前页面的Music */
    private val _mCurrentMusic = MutableLiveData<Song>()
    val mCurrentMusic: LiveData<Song>
        get() = _mCurrentMusic

    /** 当前音乐播放列表 */
    private val _mCurrentMusicList = MutableLiveData<List<Song>>()
    val mCurrentMusicList: LiveData<List<Song>>
        get() = _mCurrentMusicList

    /** 当前播放的进度 */
    var mCurrentProgress: Float = 0f

    /** 当前播放器状态 */
    var mCurrentPlayState: MusicService.PlayState = MusicService.PlayState.NOPLAYING

    /** 进度条状态，在搜索时会打开，搜索结束列表更新完成后会关闭 */
    private val _mProgressState = MutableLiveData<ProgressState>()
    val mProgressState: LiveData<ProgressState>
        get() = _mProgressState

    /** 当前播放歌曲的进度 */
    var mCurrentDuration = 0

    /**
     * 当前播放歌曲的Url
     */
    var mCurrentMusicUrl:String = ""

    /** 设置 [cn.govast.vmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
    fun setCurrentMusic(song: Song) {
        _mCurrentMusic.postValue(song)
    }

    /** 设置 [cn.govast.vmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
    fun setCurrentMusic(index: Int) {
        _mCurrentMusicList.value?.get(index)?.apply {
            setCurrentMusic(this)
        }
    }

    /**
     * 更新播放列表，如果 [playState] 为 [MusicService.PlayState.NOPLAYING]
     * 则会将当前列表的第一个作为当前的播放歌曲
     *
     * @param list
     */
    fun updateMusicList(list: List<Song>,playState: MusicService.PlayState) {
        _mCurrentMusicList.postValue(list)
        if(playState == MusicService.PlayState.NOPLAYING){
            _mCurrentMusic.postValue(list[0])
            mCurrentDuration = list[0].duration
        }
    }

    /**
     * 更新Progress状态
     *
     * @param state
     */
    fun updateProgressState(state: ProgressState) {
        _mProgressState.postValue(state)
    }

}