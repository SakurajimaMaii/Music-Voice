package cn.govast.gmusic.ui.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import cn.govast.gmusic.databinding.ActivityStartBinding
import cn.govast.gmusic.viewModel.StartVM
import cn.govast.vasttools.activity.VastVbVmActivity
import cn.govast.vasttools.extension.cast

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

class StartActivity: VastVbVmActivity<ActivityStartBinding, StartVM>() {

    private val navHostFragment by lazy {
        cast<NavHostFragment>(getBinding().fragmentContainerView)
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}