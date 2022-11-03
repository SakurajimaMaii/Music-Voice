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

import java.io.Serializable

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/3
// Description: 
// Documentation:
// Reference:

/**
 * 用来额外的携带 [music] 的播放信息
 *
 * @property music 当前播放的音乐对象
 * @property currentProgress 当前的播放进度
 * @property musicUrl 当前音乐的Url
 */
data class MusicPlayWrapper(
    val music: Music, var currentProgress: Float, val musicUrl: String
):Serializable