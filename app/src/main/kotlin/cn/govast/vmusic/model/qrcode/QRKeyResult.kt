package cn.govast.vmusic.model.qrcode

import cn.govast.vasttools.network.base.BaseApiRsp

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

class QRCodeKey(
    val code: Int,
    val data: Data
):BaseApiRsp

data class Data(
    val code: Int,
    val unikey: String
)