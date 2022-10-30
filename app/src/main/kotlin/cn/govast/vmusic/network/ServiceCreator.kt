package cn.govast.vmusic.network

import cn.govast.vmusic.Constant

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

object ServiceCreator: BaseServiceCreator() {

    override fun setBaseUrl(): String {
        return Constant.ROOT_URL
    }

}