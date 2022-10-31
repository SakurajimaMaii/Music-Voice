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

package cn.govast.vmusic.network.service


import cn.govast.vmusic.model.qrcode.GenQRCodeOption
import cn.govast.vmusic.model.qrcode.QRCodeCheck
import cn.govast.vmusic.model.qrcode.QRCodeInfo
import cn.govast.vmusic.model.qrcode.QRCodeKey
import cn.govast.vmusic.model.captcha.Captcha
import cn.govast.vmusic.model.captcha.CaptchaResult
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

interface LoginNetService {

    /**
     * 创建二维码Key
     *
     * @param timestamp 时间戳
     * @return 二维码Key
     */
    @POST("/login/qr/key")
    suspend fun generateQRCode(): QRCodeKey

    /**
     * 二维码生成接口
     *
     * @param genQRCodeOption 二维码生成参数
     * @return 二维码生成信息
     */
    @POST("/login/qr/create")
    suspend fun getQRCode(
        @Body genQRCodeOption: GenQRCodeOption
    ): QRCodeInfo

    /**
     * 检查二维码状态
     *
     * @return 二维码检查结果
     */
    @POST("/login/qr/check")
    suspend fun checkQRCode(
        @Query("key") key: String
    ): QRCodeCheck

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @return 验证码获取结果
     */
    @POST("/captcha/sent")
    suspend fun getCaptcha(
        @Query("phone") phone: String
    ): Captcha

    /**
     * 手机验证码登录
     *
     * @param phone 手机号
     * @param captcha 验证码
     */
    @POST("/login/cellphone")
    suspend fun phoneLogin(
        @Query("phone") phone: String,
        @Query("captcha") captcha: String
    ): CaptchaResult

}