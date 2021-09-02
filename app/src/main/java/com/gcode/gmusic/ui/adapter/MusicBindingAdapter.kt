package com.gcode.gmusic.ui.adapter

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.gmusic.BR
import com.example.gmusic.R
import com.gcode.gmusic.utils.AppUtils
import com.gcode.tools.adapter.BaseUtilBindingAdapter
import com.gcode.tools.adapter.BaseUtilItem
import com.gcode.tools.utils.MsgWindowUtils


/**
 *作者:created by HP on 2021/8/29 17:52
 *邮箱:sakurajimamai2020@qq.com
 */
class MusicBindingAdapter(items: MutableList<BaseUtilItem>) : BaseUtilBindingAdapter(items) {
    override fun setVariableId(): Int {
        return BR.item
    }
}