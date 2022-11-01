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
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.govast.vmusic.ui.adapter.MusicAdapter
import cn.govast.vmusic.databinding.FragmentPlayMusicBinding
import cn.govast.vmusic.service.musicplay.MusicService
import cn.govast.vmusic.constant.Order
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.ui.base.sendOrderIntent
import cn.govast.vmusic.ui.components.SpacesItemDecoration
import cn.govast.vmusic.viewModel.MainSharedVM
import cn.govast.vastadapter.AdapterClickListener
import cn.govast.vasttools.fragment.VastVbVmFragment
import cn.govast.vmusic.model.net.music.search.Song
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/18 20:35
// Description:
// Documentation:

class MusicPlayFragment : VastVbVmFragment<FragmentPlayMusicBinding, MainSharedVM>(),
    UIStateListener {

    private lateinit var slideInLeftAnimationAdapter: SlideInLeftAnimationAdapter

    // 歌曲适配器
    private val mMusicAdapter by lazy {
        MusicAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initUIState()
    }

    override fun initUIState() {
        getViewModel().mCurrentMusicList.observe(requireActivity()) {
            if(mMusicAdapter.itemCount != 0){
                mMusicAdapter.submitList(ArrayList<Song>())
            }
            mMusicAdapter.submitList(it)
            getBinding().musicRecycler.visibility = View.VISIBLE
            getViewModel().updateProgressState(MainSharedVM.ProgressState.HIDE)
        }
    }

    override fun initUI() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mMusicAdapter.registerClickEvent(object : AdapterClickListener {
            override fun onItemClick(view: View, pos: Int) {
                mMusicAdapter.getMusicByPos(pos).also {
                    getViewModel().setCurrentMusic(it)
                }
                sendOrderIntent(Order.NOW) { intent ->
                    intent.putExtra(MusicService.NOW, pos)
                }
            }
        })
        slideInLeftAnimationAdapter = SlideInLeftAnimationAdapter(mMusicAdapter)
        slideInLeftAnimationAdapter.apply {
            setInterpolator(OvershootInterpolator())
            setDuration(800)
            setFirstOnly(false)
        }
        getBinding().musicRecycler.apply {
            addItemDecoration(SpacesItemDecoration(5))
            this.layoutManager = layoutManager
            this.adapter = slideInLeftAnimationAdapter
        }
    }

}