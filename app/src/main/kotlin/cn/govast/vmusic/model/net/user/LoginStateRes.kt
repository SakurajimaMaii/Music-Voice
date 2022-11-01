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

package cn.govast.vmusic.model.net.user

import cn.govast.vasttools.network.base.BaseApiRsp

/**
 * 登录状态检查返回结果
 *
 * @property data
 */
data class LoginStateRes(
    val data: Data
) : BaseApiRsp {
    override fun isEmpty(): Boolean {
        return data.profile == null
    }
}

data class Data(
    val account: Account,
    val code: Int,
    val profile: UserProfile?
)

data class Account(
    val anonimousUser: Boolean,
    val ban: Int,
    val baoyueVersion: Int,
    val createTime: Long,
    val donateVersion: Int,
    val id: Long,
    val paidFee: Boolean,
    val status: Int,
    val tokenVersion: Int,
    val type: Int,
    val userName: String,
    val vipType: Int,
    val whitelistAuthority: Int
)