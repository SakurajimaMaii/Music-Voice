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

package cn.govast.vmusic.model.net.music.search

import cn.govast.vmusic.R
import cn.govast.vmusic.utils.TimeUtils
import cn.govast.vastadapter.AdapterItem
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 歌曲专辑信息
 *
 * @property a
 * @property album 专辑
 * @property alia
 * @property artist 艺术家
 * @property cd
 * @property cf
 * @property copyright
 * @property cp
 * @property crbt
 * @property djId
 * @property duration 歌曲时长
 * @property entertainmentTags
 * @property fee
 * @property ftype
 * @property h
 * @property hr
 * @property id
 * @property l
 * @property m
 * @property mark
 * @property mst
 * @property mv
 * @property name
 * @property no
 * @property noCopyrightRcmd
 * @property originCoverType
 * @property originSongSimpleData
 * @property pop
 * @property privilege
 * @property pst
 * @property publishTime
 * @property resourceState
 * @property rt
 * @property rtUrl
 * @property rtUrls
 * @property rtype
 * @property rurl
 * @property s_id
 * @property single
 * @property songJumpInfo
 * @property sq
 * @property st
 * @property t
 * @property tagPicList
 * @property tns
 * @property v
 * @property version
 */
data class Song(
    val a: Any,
    @SerializedName("al") val album: Album,
    val alia: List<String>,
    @SerializedName("ar") val artist: List<Artist>,
    val cd: String,
    val cf: String,
    val copyright: Int,
    val cp: Int,
    val crbt: Any,
    val djId: Int,
    @SerializedName("dt") val duration: Int,
    val entertainmentTags: Any,
    val fee: Int,
    val ftype: Int,
    val h: H,
    val hr: Hr,
    val id: Int,
    val l: L,
    val m: M,
    val mark: Long,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: Any,
    val originCoverType: Int,
    val originSongSimpleData: Any,
    val pop: Int,
    val privilege: Privilege,
    val pst: Int,
    val publishTime: Long,
    val resourceState: Boolean,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Int,
    val rurl: Any,
    val s_id: Int,
    val single: Int,
    val songJumpInfo: Any,
    val sq: Sq,
    val st: Int,
    val t: Int,
    val tagPicList: Any,
    val tns: List<String>,
    val v: Int,
    val version: Int
) : AdapterItem, Serializable {

    fun getDurationStr() = TimeUtils.timeParse(duration.toLong())

    override fun getBindType(): Int {
        return R.layout.rv_item_music
    }
}