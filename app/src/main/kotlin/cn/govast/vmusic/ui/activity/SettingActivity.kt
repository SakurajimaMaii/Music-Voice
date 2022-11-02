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

import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import cn.govast.vasttools.activity.VastVbActivity
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.utils.ActivityUtils
import cn.govast.vmusic.R
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.broadcast.ForceOfflineBroadcast
import cn.govast.vmusic.databinding.ActivitySettingBinding
import cn.govast.vmusic.ui.base.UIStateListener

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

class SettingActivity: VastVbActivity<ActivitySettingBinding>(),UIStateListener {

    companion object{
        private const val LOGIN_OUT_INDEX = 0
        private const val INFO_INDEX = 1
    }

    /**
     * 接收退出广播
     */
    private val mForceOfflineBroadcast by lazy {
        ForceOfflineBroadcast()
    }

    private val navHostFragment by lazy {
        cast<NavHostFragment>(getBinding().settingFragments.getFragment())
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivity(this)
        LocalBroadcastManager.getInstance(this).registerReceiver(mForceOfflineBroadcast,
            IntentFilter(BConstant.ACTION_LOGIN_OUT)
        )
        // 定义标签栏
        setSupportActionBar(getBinding().topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mForceOfflineBroadcast)
    }

    override fun initUIState() {

    }

    override fun initUI() {
        getBinding().topAppBar.apply {
            setNavigationOnClickListener{
                finish()
            }
        }
        // 设置tabLayout点击事件
        getBinding().tabLayout.apply {
            getTabAt(LOGIN_OUT_INDEX)?.view?.setOnClickListener {
                navController.navigate(R.id.userSettingLoginFragment)
            }
            getTabAt(INFO_INDEX)?.view?.setOnClickListener {
                navController.navigate(R.id.aboutFragment)
            }
        }
    }

}