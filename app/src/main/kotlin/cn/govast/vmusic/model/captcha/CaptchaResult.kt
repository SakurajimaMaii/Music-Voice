package cn.govast.vmusic.model.captcha

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
) : BaseApiRsp