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

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.govast.vasttools.fragment.VastVbVmFragment
import cn.govast.vmusic.R
import cn.govast.vmusic.databinding.FragmentInitBinding
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.viewModel.StartVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/31
// Description: 
// Documentation:
// Reference:

class InitFragment : VastVbVmFragment<FragmentInitBinding, StartVM>(), UIStateListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initUIState()
    }

    override fun initUIState() {
        getViewModel().progress.observe(requireActivity()){
            getBinding().loadingProgress.progress = it.toFloat()
            if(it.toFloat() == getBinding().loadingProgress.max){
                // 启动注册页
                findNavController().navigate(R.id.registerFragment)
            }
        }
    }

    override fun initUI() {
        getViewModel().isFirst.apply {
            if (this) {
                lifecycleScope.launch(Dispatchers.IO) {
                    getViewModel().loadingArea()
                }
            } else {
                findNavController().navigate(R.id.registerFragment)
            }
        }
        getBinding().loadingProgress.max = 2990f
    }

}