package cn.govast.vmusic.broadcast

import android.content.Context
import android.content.Intent
import cn.govast.vasttools.broadcastreceiver.VastBroadcastReceiver
import cn.govast.vasttools.helper.ContextHelper
import cn.govast.vasttools.utils.ActivityUtils
import cn.govast.vmusic.ui.activity.StartActivity

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

/** 接收广播，重启[cn.govast.vmusic.ui.activity.StartActivity] */
class ForceOfflineBroadcast: VastBroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        ActivityUtils.finishAllActivity()
        val mContext = context ?: ContextHelper.getAppContext()
        val mIntent = Intent(mContext,StartActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        mContext.startActivity(mIntent)
    }

}