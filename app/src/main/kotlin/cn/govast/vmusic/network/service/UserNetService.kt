package cn.govast.vmusic.network.service

import cn.govast.vmusic.model.user.LoginStateRes
import retrofit2.http.POST

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

interface UserNetService {

    /**
     * 获取登录状态
     *
     * @return
     */
    @POST("/login/status")
    suspend fun loginState(): LoginStateRes

}