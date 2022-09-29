package cn.govast.gmusic.utils

import android.os.Build
import cn.govast.gmusic.BuildConfig
import java.time.Instant

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

object TimeUtils {

    fun getTimestamp():String =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Instant.now().epochSecond.toString()
        }else{
            System.currentTimeMillis().toString()
        }

}