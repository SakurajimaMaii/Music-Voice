package com.gcode.gmusic.ui.adapter

import android.content.Context
import com.example.gmusic.BR
import com.gcode.vastadapter.base.VastBindAdapter
import com.gcode.vastadapter.interfaces.VastBindAdapterItem

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/8/29 17:52
// Description:
// Documentation:

class MusicBindingAdapter(
    mContext:Context,
    items: MutableList<VastBindAdapterItem>
) : VastBindAdapter(items,mContext) {
    override fun setVariableId(): Int {
        return BR.item
    }
}