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

package cn.govast.vmusic.model.net.qrcode

import cn.govast.vasttools.network.base.BaseApiRsp

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

/**
 * 二维码生成结果返回值
 *
 * @property code 状态码
 * @property data 返回的二维码信息
 */
class QRCodeKey(
    val code: Int,
    val data: Data
) : BaseApiRsp {

    /**
     * 返回的二维码信息
     *
     * @property code 状态码
     * @property unikey 二维码Key
     */
    data class Data(
        val code: Int,
        val unikey: String
    )
}