package cn.govast.gmusic.utils

import android.app.Application
import cn.govast.vasttools.config.ToolsConfig

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 15:01
// Description:
// Documentation:

class AppUtils:Application() {

    override fun onCreate() {
        super.onCreate()
        ToolsConfig.init(this)
    }

}