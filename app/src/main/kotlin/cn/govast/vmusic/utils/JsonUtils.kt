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

import android.content.Context
import android.content.res.AssetManager
import cn.govast.vasttools.helper.ContextHelper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/31
// Description: 
// Documentation:
// Reference:

object JsonUtils {
    /**
     * 读取assets本地json
     * @param fileName
     * @return
     */
    fun getJson(fileName: String): String {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager: AssetManager = ContextHelper.getAppContext().assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}