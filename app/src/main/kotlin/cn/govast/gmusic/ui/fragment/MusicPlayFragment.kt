package cn.govast.gmusic.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.govast.gmusic.adapter.MusicAdapter
import cn.govast.gmusic.databinding.FragmentPlayMusicBinding
import cn.govast.gmusic.service.MusicBackgroundService
import cn.govast.gmusic.ui.base.Order
import cn.govast.gmusic.ui.base.UIStateListener
import cn.govast.gmusic.ui.base.sendOrderIntent
import cn.govast.gmusic.ui.components.SpacesItemDecoration
import cn.govast.gmusic.viewModel.MainSharedVM
import cn.govast.vastadapter.AdapterClickListener
import cn.govast.vasttools.fragment.VastVbVmFragment
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
        getViewModel().mSongList.observe(requireActivity()) {
            mMusicAdapter.submitList(it)
        }
    }

    override fun initUIObserver() {

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
                    intent.putExtra(MusicBackgroundService.NOW, pos)
                }
            }
        })
        slideInLeftAnimationAdapter = SlideInLeftAnimationAdapter(mMusicAdapter)
        slideInLeftAnimationAdapter.apply {
            setInterpolator(OvershootInterpolator())
            setDuration(800)
            setFirstOnly(false)
        }
        getBinding().localMusicRv.apply {
            addItemDecoration(SpacesItemDecoration(5))
            this.layoutManager = layoutManager
            this.adapter = slideInLeftAnimationAdapter
        }
    }

}