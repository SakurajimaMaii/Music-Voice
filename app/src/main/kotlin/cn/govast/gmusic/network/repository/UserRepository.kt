package cn.govast.gmusic.network.repository

import cn.govast.gmusic.model.qrcode.QRCodeCheck
import cn.govast.gmusic.network.service.QRCodeNetService
import cn.govast.gmusic.network.service.UserNetService
import cn.govast.gmusic.model.user.LoginStateRes
import cn.govast.gmusic.network.ServiceCreator

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