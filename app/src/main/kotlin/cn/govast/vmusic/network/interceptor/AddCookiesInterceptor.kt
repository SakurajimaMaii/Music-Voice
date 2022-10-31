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

package cn.govast.vmusic.network.interceptor

import cn.govast.vmusic.mmkv.MMKV.userMMKV
import cn.govast.vmusic.constant.UserConstant.USER_COOKIE
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

class AddCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val time = System.currentTimeMillis().toString()
        val modifiedUrl = request
            .url
            .newBuilder()
            .addQueryParameter("timestamp", time)
        userMMKV.decodeStringSet(USER_COOKIE)?.let {cookieSet->
            modifiedUrl.addQueryParameter("cookie", cookieSet.first())
        }
        val newRequestBuilder = request.newBuilder().url(modifiedUrl.build()).build().newBuilder()
        return chain.proceed(newRequestBuilder.build())
    }

    companion object {
        var cookieAll: String? = null
    }
}