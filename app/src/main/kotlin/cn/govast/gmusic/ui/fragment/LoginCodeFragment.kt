package cn.govast.gmusic.ui.fragment

import android.os.Bundle
import android.view.View
import cn.govast.gmusic.databinding.FragmentLoginCodeBinding
import cn.govast.gmusic.network.UserServiceCreator
import cn.govast.gmusic.network.param.GenQRCodeOption
import cn.govast.gmusic.network.service.UserService
import cn.govast.gmusic.utils.BitmapUtils
import cn.govast.gmusic.utils.TimeUtils
import cn.govast.vasttools.fragment.VastVbFragment
import cn.govast.vasttools.utils.ToastUtils

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

class LoginCodeFragment : VastVbFragment<FragmentLoginCodeBinding>() {

    // 获取请求Builder
    private val requestBuilder by lazy {
        getRequestBuilder(this)
    }

    // 获取用户Service
    private val userService by lazy {
        UserServiceCreator.create(UserService::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestBuilder
            .requestWithListener({ userService.generateQRCode(TimeUtils.getTimestamp()) }) {
                onSuccess = {
                    requestBuilder.requestWithListener({
                        userService.getQRCode(
                            GenQRCodeOption(it.data.unikey,true)
                        )
                    }) {
                        onSuccess = { QRCodeInfo ->
                            getBinding().loginCode.setImageBitmap(BitmapUtils.getBitmapFromBase64(QRCodeInfo.data.qrimg))
                        }
                        onEmpty = {
                            ToastUtils.showShortMsg(requireActivity(),"二维码生成失败")
                        }
                    }
                }
            }
    }

}