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

package cn.govast.vmusic.model.net.music.play

import cn.govast.vasttools.network.base.BaseApiRsp

data class MusicUrl(
    val code: Int,
    val data: List<Data>
):BaseApiRsp{
    data class Data(
        val br: Int,
        val canExtend: Boolean,
        val code: Int,
        val effectTypes: Any,
        val encodeType: String,
        val expi: Int,
        val fee: Int,
        val flag: Int,
        val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
        val freeTrialInfo: Any,
        val freeTrialPrivilege: FreeTrialPrivilege,
        val gain: Double,
        val id: Int,
        val level: String,
        val md5: String,
        val payed: Int,
        val podcastCtrp: Any,
        val rightSource: Int,
        val size: Int,
        val time: Int,
        val type: String,
        val uf: Any,
        private val url: String,
        val urlSource: Int
    ){

        data class FreeTimeTrialPrivilege(
            val remainTime: Int,
            val resConsumable: Boolean,
            val type: Int,
            val userConsumable: Boolean
        )

        data class FreeTrialPrivilege(
            val listenType: Any,
            val resConsumable: Boolean,
            val userConsumable: Boolean
        )

        fun getUrl() = url.replace("http", "https")
    }
}