package cn.govast.gmusic.network.repository

import cn.govast.gmusic.model.qrcode.QRCodeCheck
import cn.govast.gmusic.network.service.QRCodeService
import cn.govast.gmusic.network.service.UserService
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
        ServiceCreator.create(QRCodeService::class.java)
    }

    private val mUserService by lazy {
        ServiceCreator.create(UserService::class.java)
    }

    suspend fun checkQRCode(key: String): QRCodeCheck {
        return mQRCodeService.checkQRCode(key)
    }

    suspend fun checkLoginState(): LoginStateRes {
        return mUserService.loginState()
    }

}