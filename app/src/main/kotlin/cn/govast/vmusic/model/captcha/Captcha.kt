package cn.govast.vmusic.model.captcha

import cn.govast.vasttools.network.base.BaseApiRsp
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vmusic.R

/**
 * 用户验证码请求结果
 *
 * @property code
 * @property data
 */
data class Captcha(
    val code: Int,
    val data: Any
) : BaseApiRsp {

    override fun isSuccess(): Boolean {
        return data == 200
    }

    override fun getErrorCode(): Int? {
        return if(200 != code) code else null
    }

    override fun getErrorMsg(): String {
        return if (code != 200 && data is String){
            data
        } else ResUtils.getString(R.string.err_info_get_captcha)
    }
}