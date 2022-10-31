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

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import cn.govast.vasttools.fragment.VastVbFragment
import cn.govast.vasttools.utils.AppUtils
import cn.govast.vasttools.utils.IntentUtils
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vmusic.R
import cn.govast.vmusic.constant.AuthorConstant
import cn.govast.vmusic.databinding.FragmentAboutBinding

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/31
// Description: 
// Documentation:
// Reference:

class AboutFragment : VastVbFragment<FragmentAboutBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().appIcon.apply {
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                BitmapDrawable(
                    resources,
                    AppUtils.getAppBitmap()
                ), null, null, null
            )
            text = AppUtils.getAppName()
        }

        getBinding().appLicense.setOnClickListener {
            IntentUtils.openWebPage(requireContext(), AuthorConstant.LICENSE)
        }

        getBinding().appVersion.text = String.format(ResUtils.getString(R.string.app_version),AppUtils.getVersionName())

        getBinding().appGithub.setOnClickListener {
            IntentUtils.openWebPage(requireContext(), AuthorConstant.PROJECT)
        }

        getBinding().authorGithub.setOnClickListener {
            IntentUtils.openWebPage(requireContext(), AuthorConstant.GITHUB)
        }

        getBinding().authorCSDN.setOnClickListener {
            IntentUtils.openWebPage(requireContext(), AuthorConstant.CSDN)
        }

        getBinding().authorTwitter.setOnClickListener {
            IntentUtils.openWebPage(requireContext(), AuthorConstant.TWITTER)
        }
    }

}