package cn.govast.gmusic.network.service


import cn.govast.gmusic.model.qrcode.GenQRCodeOption
import cn.govast.gmusic.model.qrcode.QRCodeCheck
import cn.govast.gmusic.model.qrcode.QRCodeInfo
import cn.govast.gmusic.model.qrcode.QRCodeKey
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

interface QRCodeNetService {

    /**
     * 创建二维码Key
     *
     * @param timestamp 时间戳
     * @return 二维码Key
     */
    @POST("/login/qr/key")
    suspend fun generateQRCode(): QRCodeKey

    /**
     * 二维码生成接口
     *
     * @param genQRCodeOption 二维码生成参数
     * @return 二维码生成信息
     */
    @POST("/login/qr/create")
    suspend fun getQRCode(
        @Body genQRCodeOption: GenQRCodeOption
    ): QRCodeInfo

    /**
     * 检查二维码状态
     *
     * @return 二维码检查结果
     */
    @POST("/login/qr/check")
    suspend fun checkQRCode(
        @Query("key") key: String
    ): QRCodeCheck

}