package cn.govast.gmusic.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

object BitmapUtils {

    fun getBitmapFromBase64(base64:String): Bitmap {
        val decode: ByteArray = Base64.decode(base64.split(",")[1], Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decode, 0, decode.size)
    }

}