package cn.govast.vmusic.viewModel

import cn.govast.vmusic.network.repository.UserRepository
import cn.govast.vmusic.model.qrcode.QRCodeCheck
import cn.govast.vasttools.livedata.NetStateLiveData
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.viewModel.VastViewModel
import kotlinx.coroutines.delay

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

class StartVM : VastViewModel() {

    val qrCodeCheck = NetStateLiveData<QRCodeCheck>()

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

}