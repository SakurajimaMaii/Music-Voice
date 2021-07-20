package com.example.gmusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmusic.BR
import com.example.gmusic.MusicBean
import com.example.gmusic.databinding.PlayMusicFragmentBinding
import com.example.gmusic.manager.PlayManager
import com.example.gmusic.utils.AppUtils
import com.example.gmusic.viewModel.MainActVM
import com.gcode.gutils.adapter.BaseBindingAdapter
import com.gcode.gutils.adapter.BaseItem

class MusicPlayFragment : Fragment() {

    companion object {
    }

    inner class MusicBindingAdapter(items: MutableList<BaseItem>) : BaseBindingAdapter(items) {
        override fun setVariableId(): Int {
            return BR.item
        }
    }

    private val mainVM: MainActVM by activityViewModels()
    private lateinit var binding: PlayMusicFragmentBinding

    private lateinit var mAdapter: MusicBindingAdapter

    override fun onStart() {
        super.onStart()

        activity?.let {
            mainVM.getLocalMusicData().observe(it) { music ->
                mAdapter = MusicBindingAdapter(music)
                mAdapter.setOnItemClickListener(object : BaseBindingAdapter.OnItemClickListener {
                    override fun onItemClick(itemView: View?, pos: Int) {
                        mainVM.apply {
                            setCurrentPlayPos(pos)
                            val localMusicBean = getMusicPyPos(pos)
                            setCurrentPlayMusic(localMusicBean)
                            PlayManager.playMusicInMusicBean(localMusicBean.id.toLong())
                        }
                    }
                })
                val layoutManager =
                    LinearLayoutManager(AppUtils.context, LinearLayoutManager.VERTICAL, false)
                binding.localMusicRv.apply {
                    this.layoutManager = layoutManager
                    this.adapter = mAdapter
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