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

package cn.govast.vmusic.model.music.search

/**
 * 歌曲专辑信息
 *
 * @property id
 * @property name
 * @property pic
 * @property picUrl 专辑照片链接
 * @property pic_str
 * @property tns
 */
data class Album(
    val id: Int,
    val name: String,
    val pic: Long,
    private val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
){
    fun getPicUrl() = picUrl.replace("http:","https:")
}