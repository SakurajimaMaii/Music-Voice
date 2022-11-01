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

package cn.govast.vmusic.network.service

import cn.govast.vmusic.model.net.user.LoginOutRes
import cn.govast.vmusic.model.net.user.LoginStateRes
import cn.govast.vmusic.model.net.user.UserAccount
import retrofit2.http.POST

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

interface UserNetService {

    /**
     * 用户退出登录
     *
     * @return
     */
    @POST("/logout")
    suspend fun loginOut(): LoginOutRes

    /**
     * 获取登录状态
     *
     * @return
     */
    @POST("/login/status")
    suspend fun loginState(): LoginStateRes

    /**
     * 获取用户信息
     *
     * @return
     */
    @POST("/user/account")
    suspend fun getUserAccount(): UserAccount
}