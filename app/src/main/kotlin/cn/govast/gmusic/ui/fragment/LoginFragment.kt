package cn.govast.gmusic.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cn.govast.gmusic.R
import cn.govast.gmusic.databinding.FragmentLoginBinding
import cn.govast.gmusic.viewModel.StartVM
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