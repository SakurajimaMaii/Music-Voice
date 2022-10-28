package cn.govast.gmusic.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cn.govast.gmusic.mmkv.MMKV.userMMKV
import cn.govast.gmusic.R
import cn.govast.gmusic.constant.UserConstant
import cn.govast.gmusic.constant.UserConstant.USER_COOKIE
import cn.govast.gmusic.databinding.FragmentLoginCodeBinding
import cn.govast.gmusic.network.ServiceCreator
import cn.govast.gmusic.model.qrcode.GenQRCodeOption
import cn.govast.gmusic.model.qrcode.QRCodeCheckState
import cn.govast.gmusic.network.service.QRCodeService
import cn.govast.gmusic.ui.base.UIStateListener
import cn.govast.gmusic.utils.BitmapUtils
import cn.govast.gmusic.viewModel.StartVM
import cn.govast.vasttools.fragment.VastVbVmFragment
import cn.govast.vasttools.utils.ToastUtils

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

class LoginCodeFragment : VastVbVmFragment<FragmentLoginCodeBinding, StartVM>(), UIStateListener {

    // 获取用户Service
    private val mQRCodeService by lazy {
        ServiceCreator.create(QRCodeService::class.java)
    }

    // 获取QRCode key
    private var qrCodeKey: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (null != userMMKV.decodeStringSet(USER_COOKIE)) {
            findNavController().navigate(R.id.mainActivity)
        } else {
            getRequestBuilder()
                .suspendWithListener({ mQRCodeService.generateQRCode() }) {
                    onSuccess = {
                        getRequestBuilder().suspendWithListener({
                            qrCodeKey = it.data.unikey
                            mQRCodeService.getQRCode(
                                GenQRCodeOption(it.data.unikey, true)
                            )
                        }) {
                            onSuccess = { QRCodeInfo ->
                                getBinding().loginCode.setImageBitmap(
                                    BitmapUtils.getBitmapFromBase64(
                                        QRCodeInfo.data.qrimg
                                    )
                                )
                                getViewModel().checkQRCode(qrCodeKey)
                            }
                            onEmpty = {
                                ToastUtils.showShortMsg("二维码生成失败")
                            }
                        }
                    }
                }
        }
        initUIObserver()
        initUIState()
    }

    override fun setVmBySelf(): Boolean {
        return true
    }

    override fun initUIState() {

    }

    override fun initUIObserver() {
        getViewModel().qrCodeCheck.observe(viewLifecycleOwner) {
            if (it.code == QRCodeCheckState.SURE.code) {
                userMMKV.encode(UserConstant.USER_COOKIE, setOf(it.cookie))
                findNavController().navigate(R.id.mainActivity)
            } else {
                getViewModel().checkQRCode(qrCodeKey)
            }
        }
    }

    override fun initUI() {

    }

}