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

package cn.govast.vmusic.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cn.govast.vasttools.fragment.VastVbVmFragment
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vmusic.R
import cn.govast.vmusic.constant.UserConstant.USER_COOKIE
import cn.govast.vmusic.databinding.FragmentLoginCodeBinding
import cn.govast.vmusic.mmkv.MMKV.userMMKV
import cn.govast.vmusic.model.net.qrcode.GenQRCodeOption
import cn.govast.vmusic.model.net.qrcode.QRCodeCheckState
import cn.govast.vmusic.network.ServiceCreator
import cn.govast.vmusic.network.service.LoginNetService
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.utils.BitmapUtils
import cn.govast.vmusic.viewModel.StartVM

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

class LoginCodeFragment : VastVbVmFragment<FragmentLoginCodeBinding, StartVM>(), UIStateListener {

    // 获取用户Service
    private val mQRCodeService by lazy {
        ServiceCreator.create(LoginNetService::class.java)
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
        initUIState()
    }

    override fun setVmBySelf(): Boolean {
        return true
    }

    override fun initUIState() {
        getViewModel().qrCodeCheck.observe(viewLifecycleOwner) {
            if (it.code == QRCodeCheckState.SURE.code) {
                userMMKV.encode(USER_COOKIE, setOf(it.cookie))
                findNavController().navigate(R.id.mainActivity)
            } else {
                getViewModel().checkQRCode(qrCodeKey)
            }
        }
    }

    override fun initUI() {

    }

}