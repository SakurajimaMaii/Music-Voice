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

package cn.govast.vmusic.utils

import cn.govast.vmusic.model.Gender

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

object InfoUtils {

    private val gender = mapOf<Int, Gender>(0 to Gender.SECRET, 1 to Gender.MAN, 2 to Gender.WOMAN)

    /**
     * 获取性别
     *
     * @see gender
     * @return 性别，默认保密
     */
    fun getGender(key:Int) = gender[key] ?: Gender.SECRET

}