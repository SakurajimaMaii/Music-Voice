package cn.govast.gmusic.model.qrcode

import cn.govast.gmusic.network.service.QRCodeService

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

/**
 * [QRCodeService.getQRCode] 参数
 *
 * @property key [QRCodeService.generateQRCode] 获取的key
 * @property qrimg true的话，会额外返回二维码图片 base64 编码
 */
data class GenQRCodeOption(val key:String,val qrimg:Boolean?)