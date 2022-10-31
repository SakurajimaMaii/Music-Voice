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

package cn.govast.vmusic.constant

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/**
 * @see Update
 */
object UpdateKey {
    /**
     * 广播会携带当前歌曲名称
     */
    const val LOAD_MUSIC_KEY = "load_music_key"

    /**
     * 广播会携带当前歌曲在列表中的index
     */
    const val MUSIC_PLAY_KEY = "music_play_key"

    /**
     * 广播会携带当前歌曲播放百分比
     */
    const val PROGRESS_KEY = "progress_key"

    /**
     * 广播会携带当前歌曲播放状态
     */
    const val PLAY_STATE_KEY = "play_state_key"
}