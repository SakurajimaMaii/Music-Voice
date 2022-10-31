/*
 * Copyright 2022 Vast Gui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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