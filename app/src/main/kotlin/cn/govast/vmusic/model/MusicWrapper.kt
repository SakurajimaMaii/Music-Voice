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

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/2
// Description: 
// Documentation:
// Reference:

/**
 * 用于在列表中展示 [music] 对象
 *
 * @property id 歌曲从服务器获取的唯一id，或者从contentProvider获取的唯一id
 * @property music 播放音乐的对象
 */
class MusicWrapper(val id: Int, val music: Music) : AdapterItem {
    /**
     * 返回布局id
     *
     * @return int
     */
    override fun getBindType(): Int {
        return R.layout.rv_item_music
    }
}