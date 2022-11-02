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

    /**
     * 登出信息
     */
    val ACTION_LOGIN_OUT = "${AppUtils.getPackageName()}.login.out"

    /**
     * 下载广播
     */
    val ACTION_DOWNLOAD = "${AppUtils.getPackageName()}.download"

    /**
     * [ACTION_DOWNLOAD] 广播的指令Key
     *
     * @see ACTION_UPDATE
     */
    val DOWNLOAD_KEY = "download_key"
}