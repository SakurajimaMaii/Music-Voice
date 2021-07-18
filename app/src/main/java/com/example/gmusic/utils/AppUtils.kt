package com.example.gmusic.utils

import android.app.Application
import android.content.Context

/**
 *作者:created by HP on 2021/7/3 15:01
 *邮箱:sakurajimamai2020@qq.com
 */
class AppUtils:Application() {
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }
}