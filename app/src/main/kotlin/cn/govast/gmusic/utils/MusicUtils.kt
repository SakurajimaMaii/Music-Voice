package cn.govast.gmusic.utils

import cn.govast.music.MusicPlayer
import cn.govast.vasttools.helper.ContextHelper

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

object MusicUtils {

    val musicPlayer by lazy {
        MusicPlayer(ContextHelper.getAppContext())
    }

}