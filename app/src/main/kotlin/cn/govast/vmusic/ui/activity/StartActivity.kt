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

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.navigation.fragment.NavHostFragment
import cn.govast.vasttools.activity.VastVbVmActivity
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.manager.filemgr.FileMgr
import cn.govast.vasttools.utils.ActivityUtils
import cn.govast.vmusic.R
import cn.govast.vmusic.databinding.ActivityStartBinding
import cn.govast.vmusic.sharedpreferences.UserSp
import cn.govast.vmusic.viewModel.StartVM
import java.io.File
import java.io.FileWriter
import java.util.concurrent.atomic.AtomicBoolean

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

    /**
     * 判断启动页是否还要展示
     */
    private var mKeepOnAtomicBool = AtomicBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivity(this)
        splashScreen.setKeepOnScreenCondition { mKeepOnAtomicBool.get() }
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            startSplashScreenExit(
                splashScreenViewProvider
            )
        }
        // 检查用户登录状态
        getViewModel().userLoginStatus()
        getViewModel().userLoginStatus.observe(this){
            it.data.profile?.also { userProfile ->
                UserSp.writeUser(userProfile)
                val file = File(FileMgr.getPath(false,FileMgr.appInternalFilesDir().path,"cookie.txt"))
                FileMgr.saveFile(file)
                FileMgr.writeFile(file,object :FileMgr.WriteEventListener{
                    override fun writeEvent(fileWriter: FileWriter) {
                        fileWriter.write(UserSp.getCookie().toString())
                    }
                })
            }
        }
        // 用户已经登录,替换导航图
        getViewModel().userLoginStatus.getState().observeState(this){
            onSuccess = {
                val graph = navController.navInflater.inflate(R.navigation.nav_start).also {
                    it.setStartDestination(R.id.nav_main)
                }
                navController.setGraph(graph, Bundle())
                mKeepOnAtomicBool.compareAndSet(true,false)
            }
            onEmpty = {
                mKeepOnAtomicBool.compareAndSet(true,false)
            }
        }
    }

    /**
     * SplashScreen 退出时执行
     */
    private fun startSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        // splashScreenView
        val splashScreenView = splashScreenViewProvider.view
        // splashIconView
        val iconView = splashScreenViewProvider.iconView

        // ScreenView alpha 动画
        val splashAlphaAnim = ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f)
        splashAlphaAnim.duration = 500
        splashAlphaAnim.interpolator = FastOutLinearInInterpolator()

        // iconView 向下移动的动画
        val translationY = ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_Y,
            iconView.translationY,
            splashScreenView.height.toFloat()
        )
        translationY.duration = 500
        translationY.interpolator = FastOutLinearInInterpolator()
        // 合并渐变动画 & 下移动画
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationY, splashAlphaAnim)
        // 开启动画
        animatorSet.start()
    }

}