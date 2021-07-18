package com.example.gmusic.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmusic.BR
import com.example.gmusic.MusicBean
import com.example.gmusic.R
import com.example.gmusic.databinding.PlayMusicFragmentBinding
import com.example.gmusic.manager.PlayManager
import com.example.gmusic.utils.AppUtils
import com.example.gmusic.viewModel.MainActVM
import com.gcode.gutils.adapter.BaseBindingAdapter
import com.gcode.gutils.adapter.BaseItem

class MusicPlayFragment : Fragment() {

    inner class MusicBindingAdapter(items: MutableList<BaseItem>) : BaseBindingAdapter(items) {
        override fun setVariableId(): Int {
            return BR.item
        }
    }

    private val mainVM:MainActVM by activityViewModels()
    private lateinit var binding:PlayMusicFragmentBinding

    override fun onStart() {
        super.onStart()

        val adapter = mainVM.getLocalMusicData().let {
            var data:MutableList<BaseItem>? = null

            activity?.let { act ->
                it.observe(act){
                    data = it
                }
            }

            data?.let { it1 -> MusicBindingAdapter(it1) }
        }
        adapter?.setOnItemClickListener(object : BaseBindingAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, pos: Int) {
                mainVM.apply {
                    setCurrentPlayPos(pos)
                    val localMusicBean = getMusicPyPos(pos) as MusicBean
                    setCurrentPlayMusic(localMusicBean)
                    PlayManager.playMusicInMusicBean(localMusicBean.id.toLong())
                }
            }
        })
        val layoutManager = LinearLayoutManager(AppUtils.context, LinearLayoutManager.VERTICAL, false)
        binding.localMusicRv.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayMusicFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

}