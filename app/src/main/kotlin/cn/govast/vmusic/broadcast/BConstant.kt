package cn.govast.vmusic.broadcast

import cn.govast.vasttools.utils.AppUtils


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

object BConstant {
    /**
     * 用于监听Activity发送的广播
     */
    val ACTION_CONTROL = "${AppUtils.getPackageName()}.control"

    /**
     * [ACTION_CONTROL] 广播的指令Key
     *
     * @see ACTION_CONTROL
     */
    val CONTROL_KEY = "control_key"

    /**
     * 用于Service更新界面显示
     */
    val ACTION_UPDATE = "${AppUtils.getPackageName()}.update"

    /**
     * [ACTION_UPDATE] 广播的指令Key
     *
     * @see ACTION_UPDATE
     */
    val UPDATE_KEY = "update_key"
}