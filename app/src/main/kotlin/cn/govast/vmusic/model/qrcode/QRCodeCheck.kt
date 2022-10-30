package cn.govast.vmusic.model.qrcode

import cn.govast.vasttools.network.base.BaseApiRsp

/**
 * 二维码检测结果
 *
 * @property code
 * @property cookie
 * @property message
 */
data class QRCodeCheck(
    val code: Int,
    val cookie: String,
    val message: String
): BaseApiRsp {
    override fun getErrorCode(): Int {
        return code
    }

    override fun getErrorMsg(): String {
        return message
    }
}