package cn.govast.vmusic.ui.activity

import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.vasttools.activity.VastVbActivity
import cn.govast.vasttools.utils.ActivityUtils
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.broadcast.ForceOfflineBroadcast
import cn.govast.vmusic.databinding.ActivitySettingBinding

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

class SettingActivity: VastVbActivity<ActivitySettingBinding>() {

    private val mForceOfflineBroadcast by lazy {
        ForceOfflineBroadcast()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ActivityUtils.addActivity(this)
        LocalBroadcastManager.getInstance(this).registerReceiver(mForceOfflineBroadcast,
            IntentFilter(BConstant.LOGIN_OUT)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mForceOfflineBroadcast)
    }

}