package com.gcode.gmusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmusic.databinding.PlayMusicFragmentBinding
import com.gcode.gmusic.ui.adapter.MusicBindingAdapter
import com.gcode.gmusic.ui.components.SpacesItemDecoration
import com.gcode.gmusic.utils.AppUtils
import com.gcode.gmusic.viewModel.MainActVM
import com.gcode.tools.adapter.BaseGcodeBindingAdapter
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter

class MusicPlayFragment : Fragment() {

    private val mainVM: MainActVM by activityViewModels()
    private lateinit var binding: PlayMusicFragmentBinding

    private lateinit var mAdapter: MusicBindingAdapter

    private lateinit var slideInLeftAnimationAdapter: SlideInLeftAnimationAdapter

    override fun onStart() {
        super.onStart()

        val spacesItemDecoration = SpacesItemDecoration(5)
        binding.localMusicRv.addItemDecoration(spacesItemDecoration)

        activity?.let {
            mainVM.getLocalMusicData().observe(it) { music ->
                mAdapter = MusicBindingAdapter(music)
                mAdapter.setOnItemClickListener(object : BaseGcodeBindingAdapter.OnItemClickListener {
                    override fun onItemClick(itemView: View?, pos: Int, itemId: Long) {
                        mainVM.apply {
                            setCurrentPlayPos(pos)
                            val localMusicBean = getMusicPyPos(pos)
                            playMusicInMusicBean(localMusicBean)
                        }
                    }
                })
                val layoutManager =
                    LinearLayoutManager(AppUtils.context, LinearLayoutManager.VERTICAL, false)
                slideInLeftAnimationAdapter = SlideInLeftAnimationAdapter(mAdapter)
                slideInLeftAnimationAdapter.apply {
                    setInterpolator(OvershootInterpolator())
                    setDuration(800)
                    setFirstOnly(false)
                }
                binding.localMusicRv.apply {
                    this.layoutManager = layoutManager
                    this.adapter = slideInLeftAnimationAdapter
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayMusicFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}