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

package cn.govast.vmusic.model.net.toplist

import cn.govast.vastadapter.AdapterItem
import cn.govast.vasttools.network.base.BaseApiRsp
import cn.govast.vmusic.R

/**
 * 获取所有榜单
 *
 * @property artistToplist
 * @property code
 * @property list
 */
data class TopList(
    val artistToplist: ArtistTopList,
    val code: Int,
    val list: List<Item>
) : BaseApiRsp {
    data class Item(
        val ToplistType: String,
        val adType: Int,
        val anonimous: Boolean,
        val artists: Any,
        val backgroundCoverId: Int,
        val backgroundCoverUrl: Any,
        val cloudTrackCount: Int,
        val commentThreadId: String,
        val coverImgId: Long,
        val coverImgId_str: String,
        val coverImgUrl: String,
        val createTime: Long,
        val creator: Any,
        val description: String,
        val englishTitle: Any,
        val highQuality: Boolean,
        val id: Long,
        val name: String,
        val newImported: Boolean,
        val opRecommend: Boolean,
        val ordered: Boolean,
        val playCount: Long,
        val privacy: Int,
        val recommendInfo: Any,
        val specialType: Int,
        val status: Int,
        val subscribed: Any,
        val subscribedCount: Int,
        val subscribers: List<Any>,
        val tags: List<String>,
        val titleImage: Int,
        val titleImageUrl: Any,
        val totalDuration: Int,
        val trackCount: Int,
        val trackNumberUpdateTime: Long,
        val trackUpdateTime: Long,
        val tracks: Any,
        val updateFrequency: String,
        val updateTime: Long,
        val userId: Long
    ):AdapterItem{
        override fun getBindType(): Int {
            return R.layout.rv_item_top_list
        }
    }

    data class ArtistTopList(
        val coverUrl: String,
        val name: String,
        val position: Int,
        val upateFrequency: String,
        val updateFrequency: String
    )
}