package cn.govast.gmusic.network.interceptor

import cn.govast.gmusic.mmkv.MMKV.userMMKV
import cn.govast.gmusic.constant.UserConstant.USER_COOKIE
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/29
// Description: 
// Documentation:
// Reference:

class AddCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val time = System.currentTimeMillis().toString()
        val modifiedUrl = request
            .url
            .newBuilder()
            .addQueryParameter("timestamp", time)
        userMMKV.decodeStringSet(USER_COOKIE)?.let {cookieSet->
            modifiedUrl.addQueryParameter("cookie", cookieSet.first())
        }
        val newRequestBuilder = request.newBuilder().url(modifiedUrl.build()).build().newBuilder()
        return chain.proceed(newRequestBuilder.build())
    }

    companion object {
        var cookieAll: String? = null
    }
}