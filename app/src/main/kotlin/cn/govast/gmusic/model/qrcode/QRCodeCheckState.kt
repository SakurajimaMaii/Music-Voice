package cn.govast.gmusic.model.qrcode


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/27
// Description: 
// Documentation:
// Reference:

/**
 * 二维码检测状态
 *
 * @property code
 */
enum class QRCodeCheckState(val code: Int){
    OUT_TIME(800),
    WAITING(801),
    WAITING_SURE(802),
    SURE(803)
}