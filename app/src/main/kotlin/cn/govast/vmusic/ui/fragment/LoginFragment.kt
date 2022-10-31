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
import androidx.databinding.DataBindingUtil.getBinding
import androidx.navigation.fragment.findNavController
import cn.govast.vmusic.R
import cn.govast.vmusic.databinding.FragmentLoginBinding
import cn.govast.vmusic.viewModel.StartVM
import cn.govast.vasttools.fragment.VastVbVmFragment
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vmusic.constant.UserConstant
import cn.govast.vmusic.mmkv.MMKV
import cn.govast.vmusic.ui.base.UIStateListener

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

/** 用户手机验证码登录 */
class LoginFragment : VastVbVmFragment<FragmentLoginBinding, StartVM>(), UIStateListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initUIState()
    }

    override fun initUIState() {
        getViewModel().cellphoneLogin.observe(requireActivity()){
            MMKV.userMMKV.encode(UserConstant.USER_COOKIE, setOf(it.cookie))
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        }
    }

    override fun initUI() {
        getBinding().qrcodeLogin.setOnClickListener {
            findNavController().navigate(R.id.loginCodeFragment)
        }
        getBinding().getCaptcha.setOnClickListener {
            val phone = getBinding().phoneEdit.text?.trim().toString()
            getViewModel().getCaptcha(phone)
        }
        getBinding().loginBtn.setOnClickListener {
            val phone = getBinding().phoneEdit.text?.trim().toString()
            val captcha = getBinding().captchaEdit.text?.trim().toString()
            getViewModel().phoneLogin(phone, captcha)
        }
    }

}