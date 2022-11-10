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
import cn.govast.vmusic.databinding.FragmentRankingBinding
import cn.govast.vmusic.ui.adapter.TopListAdapter
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.viewModel.RankingVM

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/10
// Description: 
// Documentation:
// Reference:

class RankingFragment: VastVbVmFragment<FragmentRankingBinding,RankingVM>(),UIStateListener {

    private val mAdapter by lazy {
        TopListAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().topList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        getViewModel().getTopList()
        initUIState()
    }

    override fun initUIState() {
        getViewModel().topList.observe(requireActivity()){
            mAdapter.submitList(it.list)
        }
    }

    override fun initUI() {

    }

}