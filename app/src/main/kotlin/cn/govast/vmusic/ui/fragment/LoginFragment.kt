package cn.govast.vmusic.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cn.govast.vmusic.R
import cn.govast.vmusic.databinding.FragmentLoginBinding
import cn.govast.vmusic.viewModel.StartVM
import cn.govast.vasttools.fragment.VastVbVmFragment

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

class LoginFragment: VastVbVmFragment<FragmentLoginBinding, StartVM>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().qrcodeLogin.setOnClickListener {
            findNavController().navigate(R.id.loginCodeFragment)
        }
    }

}