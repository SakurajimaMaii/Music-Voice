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
 * 用户账号信息
 *
 * @property account
 * @property code
 * @property profile
 */
data class UserAccount(
    val account: Account,
    val code: Int,
    val profile: Profile
) : BaseApiRsp {

    data class Account(
        val anonimousUser: Boolean,
        val ban: Int,
        val baoyueVersion: Int,
        val createTime: Long,
        val donateVersion: Int,
        val id: Int,
        val paidFee: Boolean,
        val status: Int,
        val tokenVersion: Int,
        val type: Int,
        val userName: String,
        val vipType: Int,
        val whitelistAuthority: Int
    )

    data class Profile(
        val accountStatus: Int,
        val accountType: Int,
        val anchor: Boolean,
        val authStatus: Int,
        val authenticated: Boolean,
        val authenticationTypes: Int,
        val authority: Int,
        val avatarDetail: Any,
        val avatarImgId: Long,
        val avatarUrl: String,
        val backgroundImgId: Long,
        val backgroundUrl: String,
        val birthday: Long,
        val city: Int,
        val createTime: Long,
        val defaultAvatar: Boolean,
        val description: Any,
        val detailDescription: Any,
        val djStatus: Int,
        val expertTags: Any,
        val experts: Any,
        val followed: Boolean,
        val gender: Int,
        val lastLoginIP: String,
        val lastLoginTime: Long,
        val locationStatus: Int,
        val mutual: Boolean,
        val nickname: String,
        val province: Int,
        val remarkName: Any,
        val shortUserName: String,
        val signature: String,
        val userId: Int,
        val userName: String,
        val userType: Int,
        val vipType: Int,
        val viptypeVersion: Long
    )

}