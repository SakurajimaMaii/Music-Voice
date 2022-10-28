package cn.govast.gmusic.mmkv

import cn.govast.gmusic.constant.UserConstant
import com.tencent.mmkv.MMKV

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/27
// Description: 
// Documentation:
// Reference:

object MMKV {
    // 获取存储用户的信息的mmkv
    val userMMKV = MMKV.mmkvWithID(UserConstant.USER_FILE)
}