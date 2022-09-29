package cn.govast.gmusic.ui.fragment

import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.govast.gmusic.databinding.FragmentPlayMusicBinding
import cn.govast.gmusic.ui.adapter.MusicBindingAdapter
import cn.govast.gmusic.ui.components.SpacesItemDecoration
import cn.govast.gmusic.viewModel.MainActVM
import cn.govast.vasttools.fragment.VastVbVmFragment
import com.gcode.vastadapter.base.VastBindAdapter
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/18 20:35
// Description:
// Documentation:

class MusicPlayFragment : VastVbVmFragment<FragmentPlayMusicBinding, MainActVM>() {

    private lateinit var mAdapter: MusicBindingAdapter

    private lateinit var slideInLeftAnimationAdapter: SlideInLeftAnimationAdapter

    override fun onStart() {
        super.onStart()

        val spacesItemDecoration = SpacesItemDecoration(5)
        getBinding().localMusicRv.addItemDecoration(spacesItemDecoration)

        activity?.let {
            getViewModel().getLocalMusicData().observe(it) { music ->
                mAdapter = MusicBindingAdapter(requireActivity(), music)
                mAdapter.setOnItemClickListener(object : VastBindAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        getViewModel().apply {
                            setCurrentPlayPos(position)
                            val localMusicBean = getMusicPyPos(position)
                            playMusicInMusicBean(localMusicBean)
                        }
                    }
                })
                val layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                slideInLeftAnimationAdapter = SlideInLeftAnimationAdapter(mAdapter)
                slideInLeftAnimationAdapter.apply {
                    setInterpolator(OvershootInterpolator())
                    setDuration(800)
                    setFirstOnly(false)
                }
                getBinding().localMusicRv.apply {
                    this.layoutManager = layoutManager
                    this.adapter = slideInLeftAnimationAdapter
                }
            }
        }
    }

}