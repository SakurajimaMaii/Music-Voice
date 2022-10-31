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
 * [cn.govast.vmusic.service.MusicService] 更新广播通知
 *
 * @property ON_MUSIC_INIT_LOADED 这条指令尽量不要由用户发送，这个专门用来初次启动应用更新的
 */
enum class Update {
    ON_MUSIC_INIT_LOADED,
    ON_MUSIC_LOADED,
    ON_MUSIC_PLAY,
    ON_PROGRESS,
    ON_PLAY_STATE
}