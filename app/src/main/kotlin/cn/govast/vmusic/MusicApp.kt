package cn.govast.vmusic

import android.app.Application
import cn.govast.vasttools.config.ToolsConfig
import com.tencent.mmkv.MMKV

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

class MusicApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ToolsConfig.init(this)
        MMKV.initialize(this)
    }

}