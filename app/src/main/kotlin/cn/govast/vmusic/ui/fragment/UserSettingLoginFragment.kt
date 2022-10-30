package cn.govast.vmusic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.vasttools.fragment.VastVbFragment
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.constant.UserConstant
import cn.govast.vmusic.databinding.FragmentUserLoginSettingBinding
import cn.govast.vmusic.mmkv.MMKV.userMMKV

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

class UserSettingLoginFragment : VastVbFragment<FragmentUserLoginSettingBinding>() {

    private val emptyCookie:String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().userLoginOutBtn.setOnClickListener {
            userMMKV.encode(UserConstant.USER_COOKIE,emptyCookie) // 清空用户Cookie数据
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent(BConstant.LOGIN_OUT)) // 发送退出广播
        }
    }

}