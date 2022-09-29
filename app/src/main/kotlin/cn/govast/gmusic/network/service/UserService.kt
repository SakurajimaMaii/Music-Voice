package cn.govast.gmusic.network.service

import cn.govast.gmusic.network.response.QRCodeInfo
import cn.govast.gmusic.network.param.GenQRCodeOption
import cn.govast.gmusic.network.response.QRCodeKey
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

interface UserService {

    /**
     * 创建二维码Key
     *
     * @param timestamp 时间戳
     * @return 二维码Key
     * @since 0.0.9
     */
    @POST("/login/qr/key")
    suspend fun generateQRCode(@Query("timestamp") timestamp: String): QRCodeKey

    /**
     * 二维码生成接口
     *
     * @param genQRCodeOption 二维码生成参数
     * @return 二维码生成信息
     * @since 0.0.9
     */
    @POST("/login/qr/create")
    suspend fun getQRCode(
        @Body genQRCodeOption: GenQRCodeOption
    ): QRCodeInfo

}