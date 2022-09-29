package cn.govast.gmusic.model

import cn.govast.gmusic.R
import com.gcode.vastadapter.interfaces.VAapClickEventListener
import com.gcode.vastadapter.interfaces.VAdpLongClickEventListener
import com.gcode.vastadapter.interfaces.VastBindAdapterItem

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/4/13 13:25
// Description:
// Documentation:

data class MusicBean @JvmOverloads constructor(
    val id:Int,
    var song: String, //歌手名称
    var singer: String?, //专辑名称
    var album: String?, //歌曲时长
    var duration: Long?, //歌曲路径
    override var vbAapClickEventListener: VAapClickEventListener? = null,
    override var vbAdpLongClickEventListener: VAdpLongClickEventListener? = null
): VastBindAdapterItem {

    override fun getVBAdpItemType(): Int {
        return R.layout.rv_item_local_music
    }

}