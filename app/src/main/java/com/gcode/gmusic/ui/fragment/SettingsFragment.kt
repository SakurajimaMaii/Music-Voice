package com.gcode.gmusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.gmusic.R
import com.example.gmusic.databinding.SettingFragmentBinding

class SettingsFragment : Fragment() {

    private lateinit var binding:SettingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.setting_fragment,container,false)
        return binding.root
    }
}