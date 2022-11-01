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

import cn.govast.vmusic.model.net.music.search.Song
import cn.govast.vmusic.service.musicplay.MusicService


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/31
// Description: 
// Documentation:
// Reference:

/** 用于管理当前播放音乐 */
class CurrentPlayMusicMgr() {

    private lateinit var musicList: List<Song>
    private var index = 0

    /**
     * 初始化管理器
     *
     * @param musicList
     */
    fun initMgr(musicList: List<Song>) {
        this.musicList = musicList
    }

    /**
     * 获取当前播放的歌曲
     */
    fun getCurrentSong(): Song = musicList[index]

    /**
     * 获取下一首歌索引，如超过数组范围则返回0
     *
     * @return 下一首歌索引
     */
    fun getNextSong(): Song {
        index++
        if (index >= musicList.size) {
            index = 0
        }
        return musicList[index]
    }

    /**
     * 上一首更新标记即可，越界设置最后一首
     *
     * @return 上一首更新标记即可
     */
    fun getPreviewSong(): Song {
        index--
        if (index < 0) {
            index = musicList.size - 1
        }
        return musicList[index]
    }

    /**
     * 获取 [index] 指定的歌曲对象
     *
     * @param index
     * @return
     */
    fun getSongByIndex(index: Int):Song{
        this.index = index
        return  musicList[index]
    }

    /**
     * 重置播放索引信息
     *
     * @param musicListCount
     */
    fun resetMgr(musicList: List<Song>) {
        this.musicList = musicList
        index = 0
    }

    fun resetIndex(){
        index = 0
    }

    /**
     * 设置循环模式，并获取 [loopMode] 模式下要播放音乐的 index
     *
     * @param loopMode 要设置的循环模式
     */
    fun getIndexAfterUpdateLoop(loopMode: MusicService.LoopMode): Song {
        // 完成播放查看当前的循环标记位
        // 列表循环直接下一首
        // 单曲循环则歌曲标记current不需要做更新操作，下一次直接继续播放之前的音乐
        // 随机播放直接在[0,info.size()-1]范围内随机数
        return when (loopMode) {
            MusicService.LoopMode.LIST -> getNextSong()
            MusicService.LoopMode.ONE -> getCurrentSong()
            MusicService.LoopMode.RANDOM -> {
                val max = musicList.size - 1
                val min = 0
                val randomNum = System.currentTimeMillis()
                musicList[(randomNum % (max - min) + min).toInt()]
            }
        }
    }

}