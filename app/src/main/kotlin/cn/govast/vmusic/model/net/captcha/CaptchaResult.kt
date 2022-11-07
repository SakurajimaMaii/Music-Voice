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

package cn.govast.vmusic.model.net.captcha

import cn.govast.vasttools.network.base.BaseApiRsp

/**
 * 手机验证码登录结果
 *
 * @property account
 * @property bindings
 * @property code
 * @property cookie
 * @property loginType
 * @property profile
 * @property token
 */
data class CaptchaResult(
    val account: Account,
    val bindings: List<Binding>,
    val code: Int,
    val cookie: String,
    val loginType: Int,
    val profile: Profile,
    val token: String
) : BaseApiRsp {
    data class Account(
        val anonimousUser: Boolean,
        val ban: Int,
        val baoyueVersion: Int,
        val createTime: Long,
        val donateVersion: Int,
        val id: Int,
        val salt: String,
        val status: Int,
        val tokenVersion: Int,
        val type: Int,
        val uninitialized: Boolean,
        val userName: String,
        val vipType: Int,
        val viptypeVersion: Long,
        val whitelistAuthority: Int
    )

    data class Binding(
        val bindingTime: Long,
        val expired: Boolean,
        val expiresIn: Int,
        val id: Long,
        val refreshTime: Int,
        val tokenJsonStr: String,
        val type: Int,
        val url: String,
        val userId: Int
    )

    /**
     * 账户信息
     *
     * @property accountStatus
     * @property authStatus
     * @property authority
     * @property avatarDetail
     * @property avatarImgId
     * @property avatarImgIdStr
     * @property avatarImgId_str
     * @property avatarUrl
     * @property backgroundImgId
     * @property backgroundImgIdStr
     * @property backgroundUrl 用户背景图信息
     * @property birthday
     * @property city 城市代码
     * @property defaultAvatar
     * @property description
     * @property detailDescription
     * @property djStatus
     * @property eventCount
     * @property expertTags
     * @property experts
     * @property followed
     * @property followeds
     * @property follows
     * @property gender 性别，详情查看[cn.govast.vmusic.utils.InfoUtils.getGender]
     * @property mutual
     * @property nickname 昵称
     * @property playlistBeSubscribedCount
     * @property playlistCount
     * @property province 省份代码
     * @property remarkName
     * @property signature
     * @property userId 用户id
     * @property userType
     * @property vipType
     */
    data class Profile(
        val accountStatus: Int,
        val authStatus: Int,
        val authority: Int,
        val avatarDetail: Any,
        val avatarImgId: Long,
        val avatarImgIdStr: String,
        val avatarImgId_str: String,
        val avatarUrl: String,
        val backgroundImgId: Long,
        val backgroundImgIdStr: String,
        val backgroundUrl: String,
        val birthday: Long,
        val city: Int,
        val defaultAvatar: Boolean,
        val description: String,
        val detailDescription: String,
        val djStatus: Int,
        val eventCount: Int,
        val expertTags: Any,
        val experts: Experts,
        val followed: Boolean,
        val followeds: Int,
        val follows: Int,
        val gender: Int,
        val mutual: Boolean,
        val nickname: String,
        val playlistBeSubscribedCount: Int,
        val playlistCount: Int,
        val province: Int,
        val remarkName: Any,
        val signature: String,
        val userId: Int,
        val userType: Int,
        val vipType: Int
    ) {
        class Experts
    }
}