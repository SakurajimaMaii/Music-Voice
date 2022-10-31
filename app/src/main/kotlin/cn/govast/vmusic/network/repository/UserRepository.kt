/*
 * Copyright 2022 Vast Gui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.govast.vmusic.network.repository

import cn.govast.vmusic.model.qrcode.QRCodeCheck
import cn.govast.vmusic.model.captcha.Captcha
import cn.govast.vmusic.model.captcha.CaptchaResult
import cn.govast.vmusic.model.user.LoginOutRes
import cn.govast.vmusic.network.service.LoginNetService
import cn.govast.vmusic.network.service.UserNetService
import cn.govast.vmusic.model.user.LoginStateRes
import cn.govast.vmusic.model.user.UserAccount
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

    suspend fun getUserAccount(): UserAccount {
        return mUserService.getUserAccount()
    }

    suspend fun loginOut():LoginOutRes{
        return mUserService.loginOut()
    }

}