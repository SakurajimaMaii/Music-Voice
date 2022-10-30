package cn.govast.vmusic.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.govast.vmusic.adapter.MusicAdapter
import cn.govast.vmusic.databinding.FragmentPlayMusicBinding
import cn.govast.vmusic.model.music.search.Song
import cn.govast.vmusic.service.MusicService
import cn.govast.vmusic.constant.Order
import cn.govast.vmusic.ui.base.UIStateListener
import cn.govast.vmusic.ui.base.sendOrderIntent
import cn.govast.vmusic.ui.components.SpacesItemDecoration
import cn.govast.vmusic.viewModel.MainSharedVM
import cn.govast.vastadapter.AdapterClickListener
import cn.govast.vasttools.fragment.VastVbVmFragment
import com.google.android.material.progressindicator.BaseProgressIndicator
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
        initUIObserver()
    }

    override fun initUIState() {

    }

    override fun initUIObserver() {
        getViewModel().mSongList.observe(requireActivity()) {
            if(mMusicAdapter.itemCount != 0){
                mMusicAdapter.submitList(ArrayList<Song>())
            }
            mMusicAdapter.submitList(it)
            getBinding().musicRecycler.visibility = View.VISIBLE
            // 设置进度指示器
            // https://github.com/material-components/material-components-android/blob/master/docs/components/ProgressIndicator.md
            getBinding().musicLoading.apply {
                hide()
            }
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
        // 设置进度指示器
        getBinding().musicLoading.apply {
            hideAnimationBehavior = BaseProgressIndicator.HIDE_INWARD
            setVisibilityAfterHide(View.GONE)
        }
    }

}