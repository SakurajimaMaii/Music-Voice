package cn.govast.vmusic.network

import cn.govast.vmusic.network.interceptor.AddCookiesInterceptor
import cn.govast.vasttools.network.RetrofitBuilder
import okhttp3.OkHttpClient

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

abstract class BaseServiceCreator: RetrofitBuilder() {

    override fun handleOkHttpClientBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(AddCookiesInterceptor())
    }

}