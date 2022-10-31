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

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.vasttools.fragment.VastVbFragment
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.constant.UserConstant
import cn.govast.vmusic.databinding.FragmentUserLoginSettingBinding
import cn.govast.vmusic.mmkv.MMKV.userMMKV
import cn.govast.vmusic.network.repository.UserRepository

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

class UserFragment : VastVbFragment<FragmentUserLoginSettingBinding>() {

    private val emptyCookie:String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().userLoginOutBtn.setOnClickListener {
            userMMKV.encode(UserConstant.USER_COOKIE,emptyCookie) // 清空用户Cookie数据
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent(BConstant.LOGIN_OUT)) // 发送退出广播
            getRequestBuilder()
                .suspendWithListener({UserRepository.loginOut()}){
                    onSuccess = {
                        ToastUtils.showShortMsg("退出登录")
                    }
                }
        }
    }

}