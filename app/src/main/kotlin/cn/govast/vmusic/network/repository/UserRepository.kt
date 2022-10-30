package cn.govast.vmusic.network.repository

import cn.govast.vmusic.model.qrcode.QRCodeCheck
import cn.govast.vmusic.model.captcha.Captcha
import cn.govast.vmusic.model.captcha.CaptchaResult
import cn.govast.vmusic.network.service.LoginNetService
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

    private val mLoginService by lazy {
        ServiceCreator.create(LoginNetService::class.java)
    }

    private val mUserService by lazy {
        ServiceCreator.create(UserNetService::class.java)
    }

    suspend fun checkQRCode(key: String): QRCodeCheck {
        return mLoginService.checkQRCode(key)
    }

    suspend fun checkLoginState(): LoginStateRes {
        return mUserService.loginState()
    }

    suspend fun getCaptcha(phone: String): Captcha {
        return mLoginService.getCaptcha(phone)
    }

    suspend fun phoneLogin(phone: String, captcha: String): CaptchaResult {
        return mLoginService.phoneLogin(phone, captcha)
    }

}