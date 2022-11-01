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

package cn.govast.vmusic.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import cn.govast.vmusic.BR
import cn.govast.vastadapter.recycleradpter.VastBindListAdapter
import cn.govast.vmusic.model.net.music.search.Song

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

/**
 * [Song] 的适配器
 *
 * @param context
 */
class MusicAdapter(context: Context):VastBindListAdapter<Song>(context, SongDiffUtil()) {

    class SongDiffUtil:DiffUtil.ItemCallback<Song>(){
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun setVariableId(): Int {
        return BR.song
    }

    /**
     * 通过 [pos] 获取歌曲对象
     *
     * @param pos
     */
    fun getMusicByPos(pos:Int):Song{
        return getItem(pos)
    }
}