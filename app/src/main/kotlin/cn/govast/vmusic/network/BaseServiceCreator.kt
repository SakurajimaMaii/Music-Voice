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

package cn.govast.vmusic.network

import cn.govast.vmusic.network.interceptor.AddCookiesInterceptor
import cn.govast.vasttools.network.RetrofitBuilder
import okhttp3.OkHttpClient

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

abstract class BaseServiceCreator: RetrofitBuilder() {

    override fun handleOkHttpClientBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(AddCookiesInterceptor())
    }

}