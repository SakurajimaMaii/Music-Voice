package cn.govast.vmusic.mmkv

import cn.govast.vmusic.constant.UserConstant
import com.tencent.mmkv.MMKV

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/27
// Description: 
// Documentation:
// Reference:

object MMKV {
    // 获取存储用户的信息的mmkv
    val userMMKV: MMKV = MMKV.mmkvWithID(UserConstant.USER_FILE)
}