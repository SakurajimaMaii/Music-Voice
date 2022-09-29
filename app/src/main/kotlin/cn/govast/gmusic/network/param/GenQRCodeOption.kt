package cn.govast.gmusic.network.param

import cn.govast.gmusic.network.service.UserService

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

/**
 * [UserService.getQRCode] 参数
 *
 * @property key [UserService.generateQRCode] 获取的key
 * @property qrimg true的话，会额外返回二维码图片 base64 编码
 * @since 0.0.9
 */
data class GenQRCodeOption(val key:String,val qrimg:Boolean?)