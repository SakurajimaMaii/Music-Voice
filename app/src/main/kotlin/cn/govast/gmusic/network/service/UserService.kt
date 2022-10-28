package cn.govast.gmusic.network.service

import cn.govast.gmusic.model.user.LoginStateRes
import retrofit2.http.POST
import retrofit2.http.Query

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

interface UserService {

    @POST("/login/status")
    suspend fun loginState(): LoginStateRes

}