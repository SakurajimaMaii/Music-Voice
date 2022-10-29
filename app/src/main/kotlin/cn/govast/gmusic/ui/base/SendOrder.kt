package cn.govast.gmusic.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.gmusic.service.MusicBackgroundService
import cn.govast.gmusic.ui.activity.MainActivity
import cn.govast.vasttools.helper.ContextHelper

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/**
 * 获取用于本地指令广播Intent
 *
 * @param order 指令，参考 [Order]
 */
@JvmOverloads
fun sendOrderIntent(order: Order, block: ((intent: Intent) -> Unit)? = null) =
    Intent(MainActivity.ACTION_CONTROL).apply {
        val bundle = Bundle().also {
            it.putSerializable(MusicBackgroundService.CONTROL, order)
        }
        putExtras(bundle)
        block?.invoke(this)
    }.also { LocalBroadcastManager.getInstance(ContextHelper.getAppContext()).sendBroadcast(it) }