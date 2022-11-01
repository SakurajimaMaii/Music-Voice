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

package cn.govast.vmusic.model.net.captcha

import cn.govast.vasttools.network.base.BaseApiRsp
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vmusic.R

/**
 * 用户验证码请求结果
 *
 * @property code
 * @property data
 */
data class Captcha(
    val code: Int,
    val data: Any
) : BaseApiRsp {

    override fun isSuccess(): Boolean {
        return data == 200
    }

    override fun getErrorCode(): Int? {
        return if(200 != code) code else null
    }

    override fun getErrorMsg(): String {
        return if (code != 200 && data is String){
            data
        } else ResUtils.getString(R.string.err_info_get_captcha)
    }
}