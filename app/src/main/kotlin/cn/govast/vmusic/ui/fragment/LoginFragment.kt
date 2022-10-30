package cn.govast.vmusic.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil.getBinding
import androidx.navigation.fragment.findNavController
import cn.govast.vmusic.R
import cn.govast.vmusic.databinding.FragmentLoginBinding
import cn.govast.vmusic.viewModel.StartVM
import cn.govast.vasttools.fragment.VastVbVmFragment
import cn.govast.vmusic.constant.UserConstant
import cn.govast.vmusic.mmkv.MMKV
import cn.govast.vmusic.ui.base.UIStateListener

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

/** 用户验证码登录 */
class LoginFragment : VastVbVmFragment<FragmentLoginBinding, StartVM>(), UIStateListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (null != MMKV.userMMKV.decodeStringSet(UserConstant.USER_COOKIE)) {
            findNavController().navigate(R.id.mainActivity)
        }
        initUI()
    }

    override fun initUIState() {
        getViewModel().cellphoneLogin.observe(this){
            MMKV.userMMKV.encode(UserConstant.USER_COOKIE, setOf(it.cookie))
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
            val captcha = getBinding().phoneEdit.text?.trim().toString()
            getViewModel().phoneLogin(phone, captcha)
        }
    }

}