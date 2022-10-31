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

package cn.govast.vmusic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import cn.govast.city.db.AreaDatabase
import cn.govast.vmusic.databinding.ActivityStartBinding
import cn.govast.vmusic.viewModel.StartVM
import cn.govast.vasttools.activity.VastVbVmActivity
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.utils.ActivityUtils
import cn.govast.vmusic.R
import cn.govast.vmusic.constant.UserConstant
import cn.govast.vmusic.manager.MusicMgr
import cn.govast.vmusic.mmkv.MMKV
import cn.govast.vmusic.service.MusicService
import cn.govast.vmusic.sharedpreferences.UserSp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

class StartActivity : VastVbVmActivity<ActivityStartBinding, StartVM>() {

    private val navHostFragment by lazy {
        cast<NavHostFragment>(getBinding().fragmentContainerView.getFragment())
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    /** 启动页项 */
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivity(this)
        startService(Intent(this, MusicService::class.java).setType(MusicMgr.getDefaultTag()))
        // 用户已经登录,替换导航图
        if (null != MMKV.userMMKV.decodeStringSet(UserConstant.USER_COOKIE)) {
            val graph = navController.navInflater.inflate(R.navigation.nav_start).also {
                it.setStartDestination(R.id.nav_main)
            }
            navController.setGraph(graph, Bundle())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MusicService::class.java).setType(MusicMgr.getDefaultTag()))
    }

}