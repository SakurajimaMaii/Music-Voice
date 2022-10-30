package cn.govast.vmusic.viewModel

import cn.govast.vmusic.network.repository.UserRepository
import cn.govast.vmusic.model.qrcode.QRCodeCheck
import cn.govast.vasttools.livedata.NetStateLiveData
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.utils.RegexUtils
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vasttools.viewModel.VastViewModel
import cn.govast.vmusic.R
import cn.govast.vmusic.model.captcha.CaptchaResult
import kotlinx.coroutines.delay

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

class StartVM : VastViewModel() {

    val qrCodeCheck = NetStateLiveData<QRCodeCheck>()

    val cellphoneLogin = NetStateLiveData<CaptchaResult>()

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
                    LogUtils.d(getDefaultTag(), errorCode.toString() + errorMsg)
                }
                onError = {
                    LogUtils.d(getDefaultTag(), it?.cause?.message)
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
                .suspendWithListener({ UserRepository.getCaptcha(phone) }) {
                    onFailed = { code, msg ->
                        ToastUtils.showShortMsg("$code $msg")
                    }
                }
        } else ToastUtils.showShortMsg(ResUtils.getString(R.string.err_info_phone))
    }

    /**
     * 手机验证码登录
     *
     * @param phone 手机号
     * @param captcha 验证码
     */
    fun phoneLogin(phone: String, captcha: String){
        getRequestBuilder()
            .suspendWithListener({UserRepository.phoneLogin(phone, captcha)}){
                onSuccess = {
                    cellphoneLogin.postValueAndSuccess(it)
                }
            }
    }

}