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
import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import cn.govast.vastadapter.recycleradpter.VastBindListAdapter
import cn.govast.vasttools.helper.ContextHelper
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vmusic.BR
import cn.govast.vmusic.R
import cn.govast.vmusic.model.MusicWrapper
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

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
class MusicAdapter(context: Context) : VastBindListAdapter<MusicWrapper>(context, SongDiffUtil()) {

    companion object {
        @BindingAdapter("imageUrl", "error")
        @JvmStatic
        fun setMusicAlbumArt(view: ShapeableImageView, url: String, error: Drawable) {
            Glide.with(ContextHelper.getAppContext()).load(url).error(error).into(view)
        }
    }

    class SongDiffUtil : DiffUtil.ItemCallback<MusicWrapper>() {
        override fun areItemsTheSame(oldItem: MusicWrapper, newItem: MusicWrapper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MusicWrapper, newItem: MusicWrapper): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun setVariableId(): Int {
        return BR.wrapper
    }

    /**
     * 通过 [pos] 获取歌曲对象
     *
     * @param pos
     */
    fun getMusicByPos(pos: Int): MusicWrapper {
        return getItem(pos)
    }
}