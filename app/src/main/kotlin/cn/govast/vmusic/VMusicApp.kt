package cn.govast.vmusic

import android.app.Application
import android.content.Intent
import cn.govast.vasttools.config.ToolsConfig
import cn.govast.vmusic.manager.MusicMgr.getDefaultTag
import cn.govast.vmusic.service.MusicService
import com.tencent.mmkv.MMKV

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

class VMusicApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ToolsConfig.init(this)
        MMKV.initialize(this)
        startService(Intent(this, MusicService::class.java).setType(getDefaultTag()))
    }

    override fun onLowMemory() {
        super.onLowMemory()
        stopService(Intent(this, MusicService::class.java).setType(getDefaultTag()))
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        stopService(Intent(this, MusicService::class.java).setType(getDefaultTag()))
    }

}