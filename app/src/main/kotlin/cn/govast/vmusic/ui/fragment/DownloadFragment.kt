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
import androidx.recyclerview.widget.LinearLayoutManager
import cn.govast.vasttools.fragment.VastVbVmFragment
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vmusic.databinding.FragmentDownloadBinding
import cn.govast.vmusic.ui.adapter.MusicAdapter
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.viewModel.MusicDownloadVM

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/1
// Description: 
// Documentation:
// Reference:

class DownloadFragment : VastVbVmFragment<FragmentDownloadBinding, MusicDownloadVM>(),
    UIStateListener {

    private val localMusicAdapter by lazy {
        MusicAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().loadLocalMusicData()
        initUI()
        initUIState()
    }

    override fun initUIState() {
        getViewModel().mCurrentDownloadList.observe(viewLifecycleOwner) {
            localMusicAdapter.submitList(it)
        }
    }

    override fun initUI() {
        getBinding().musicDownloadRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = localMusicAdapter
        }
    }

    override fun setVmBySelf(): Boolean {
        return true
    }

}