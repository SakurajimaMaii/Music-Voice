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

package cn.govast.vmusic.service.musicplay

import cn.govast.vmusic.model.Music
import cn.govast.vmusic.model.MusicPlayWrapper
import cn.govast.vmusic.model.MusicWrapper

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/31
// Description: 
// Documentation:
// Reference:

/** 用于 Service 和 Activity 进行数据交互 */
class MusicServiceDataListener {
    /** 用于监听歌曲列表更新，播放状态用于你判断某些控件是否需要更新信息 */
    var updateCurrentMusicList: ((songs: List<MusicWrapper>, currentPlayState: MusicService.PlayState) -> Unit) =
        { _, _ -> }

    /** 用于通知界面现在等待播放的歌曲 */
    var updateCurrentMusic: ((song: MusicPlayWrapper) -> Unit) = { }
}