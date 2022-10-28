package cn.govast.gmusic.model.user

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