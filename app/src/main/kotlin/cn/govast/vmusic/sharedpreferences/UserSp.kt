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

package cn.govast.vmusic.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import cn.govast.vasttools.helper.ContextHelper
import cn.govast.vmusic.extension.toFields
import cn.govast.vmusic.model.net.user.LoginStateRes

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

object UserSp {
    private const val USER_SP_FILE = "USER_SP_FILE"
    private const val USER_COOKIE = "USER_COOKIE"
    const val IsFirst = "IS_FIRST"

    private val mContext by lazy {
        ContextHelper.getAppContext()
    }
    private val mSharedPreferences by lazy {
        mContext.getSharedPreferences(USER_SP_FILE, Context.MODE_PRIVATE)
    }

    fun getSp(): SharedPreferences {
        return mSharedPreferences
    }

    fun writeUser(userProfile: LoginStateRes.Data.Profile) {
        with(mSharedPreferences.edit()) {
            userProfile.toFields().forEach { (name, value) ->
                when (value) {
                    is Double -> putLong(name, value.toRawBits())
                    is Boolean -> putBoolean(name, value)
                    is String -> putString(name, value)
                }
            }
            apply()
        }
    }

    /**
     * 设置cookie
     *
     * @param cookie
     */
    fun setCookie(cookie:String?){
        with(mSharedPreferences.edit()){
            putString(USER_COOKIE,cookie)
            apply()
        }
    }

    /**
     * 获取Cookie
     *
     * @return
     */
    fun getCookie():String? = mSharedPreferences.getString(USER_COOKIE,null)

    /** 设置首期启动数据记录 */
    fun isFirst() {
        with(mSharedPreferences.edit()) {
            putBoolean(IsFirst, false)
            apply()
        }
    }

}