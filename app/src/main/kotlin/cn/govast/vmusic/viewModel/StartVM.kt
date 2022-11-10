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

package cn.govast.vmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.govast.city.model.Area
import cn.govast.vasttools.lifecycle.StateLiveData
import cn.govast.vasttools.utils.RegexUtils
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vasttools.viewModel.VastViewModel
import cn.govast.vmusic.R
import cn.govast.vmusic.VMusicApp
import cn.govast.vmusic.model.net.captcha.CaptchaResult
import cn.govast.vmusic.model.net.qrcode.QRCodeCheck
import cn.govast.vmusic.model.net.user.LoginStateRes
import cn.govast.vmusic.network.repository.UserRepository
import cn.govast.vmusic.sharedpreferences.UserSp
import cn.govast.vmusic.utils.JsonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.lang.reflect.Type

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

class StartVM : VastViewModel() {

    val qrCodeCheck = StateLiveData<QRCodeCheck>()

    val cellphoneLogin = StateLiveData<CaptchaResult>()

    private val areas by lazy {
        JsonUtils.getJson("areas.json")
    }

    /** 应用初次加载 */
    val isFirst: Boolean
        get() = UserSp.getSp().getBoolean(UserSp.IsFirst, true)
    
    /** 地区加载进度 */
    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress

    val userLoginStatus = StateLiveData<LoginStateRes>()

    fun checkQRCode(key: String) {
        getRequestBuilder()
            .suspendWithListener({
                delay(1000)
                UserRepository.checkQRCode(key)
            }) {
                onSuccess = {
                    qrCodeCheck.postValueAndSuccess(it)
                }
                onFailed = { errorCode, errorMsg ->
                    qrCodeCheck.postFailed(errorCode, errorMsg)
                }
                onError = {
                    qrCodeCheck.postError(it)
                }
            }
    }

    /**
     * 获取验证码
     *
     * @param phone 手机号
     */
    fun getCaptcha(phone: String) {
        if (RegexUtils.isPhoneNumber(phone)) {
            getRequestBuilder()
                .suspendWithListener({ UserRepository.getCaptcha(phone) }) {}
        } else ToastUtils.showShortMsg(ResUtils.getString(R.string.err_info_phone))
    }

    /**
     * 手机验证码登录
     *
     * @param phone 手机号
     * @param captcha 验证码
     */
    fun phoneLogin(phone: String, captcha: String) {
        getRequestBuilder()
            .suspendWithListener({ UserRepository.phoneLogin(phone, captcha) }) {
                onSuccess = {
                    cellphoneLogin.postValueAndSuccess(it)
                }
                onError = {
                    it?.printStackTrace()
                }
            }
    }

    /**
     * 检查用户登录状态
     */
    fun userLoginStatus(){
        getRequestBuilder()
            .suspendWithListener({UserRepository.checkLoginState()}){
                onSuccess = {
                    userLoginStatus.postValueAndSuccess(it)
                }
                onEmpty = {
                    userLoginStatus.postEmpty()
                }
            }
    }

    /** 加载区域数据 */
    suspend fun loadingArea() {
        UserSp.getSp().getBoolean(UserSp.IsFirst, true).apply {
            if (this) {
                val gson = Gson()
                val type: Type = object : TypeToken<ArrayList<Area>>() {}.type
                val areaList = gson.fromJson<ArrayList<Area>>(areas, type)
                areaList.forEachIndexed { index, area ->
                    VMusicApp.areaDao.insertArea(area)
                    _progress.postValue(index + 1)
                }
                UserSp.isFirst()
            }
        }
    }

}