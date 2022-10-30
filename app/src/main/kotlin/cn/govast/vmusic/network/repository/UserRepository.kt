package cn.govast.vmusic.network.repository

import cn.govast.vmusic.model.qrcode.QRCodeCheck
import cn.govast.vmusic.network.service.QRCodeNetService
import cn.govast.vmusic.network.service.UserNetService
import cn.govast.vmusic.model.user.LoginStateRes
import cn.govast.vmusic.network.ServiceCreator

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

object UserRepository {

    private val mQRCodeService by lazy {
        ServiceCreator.create(QRCodeNetService::class.java)
    }

    private val mUserService by lazy {
        ServiceCreator.create(UserNetService::class.java)
    }

    suspend fun checkQRCode(key: String): QRCodeCheck {
        return mQRCodeService.checkQRCode(key)
    }

    suspend fun checkLoginState(): LoginStateRes {
        return mUserService.loginState()
    }

}