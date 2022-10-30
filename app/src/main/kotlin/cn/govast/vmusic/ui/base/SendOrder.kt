package cn.govast.vmusic.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.constant.Order
import cn.govast.vmusic.constant.Update
import cn.govast.vasttools.helper.ContextHelper

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/**
 * 向 [cn.govast.vmusic.service.MusicService] 发送广播通知
 *
 * @param order 指令，参考 [Order]
 */
@JvmOverloads
fun sendOrderIntent(order: Order, block: ((intent: Intent) -> Unit)? = null) =
    Intent(BConstant.ACTION_CONTROL).apply {
        val bundle = Bundle().also {
            it.putSerializable(BConstant.CONTROL_KEY, order)
        }
        putExtras(bundle)
        block?.invoke(this)
    }.also { LocalBroadcastManager.getInstance(ContextHelper.getAppContext()).sendBroadcast(it) }

/**
 * 广播向页面发送更新通知
 *
 * @param update
 */
fun sendUpdateIntent(update: Update, block: ((intent: Intent) -> Unit)? = null) =
    Intent(BConstant.ACTION_UPDATE).apply {
        val bundle = Bundle().also {
            it.putSerializable(BConstant.UPDATE_KEY, update)
        }
        putExtras(bundle)
        block?.invoke(this)
    }.also { LocalBroadcastManager.getInstance(ContextHelper.getAppContext()).sendBroadcast(it) }