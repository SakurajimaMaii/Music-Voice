package com.example.gmusic

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        //隐藏状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //隐藏标题栏
        supportActionBar!!.hide()
        setContentView(R.layout.splash_activity)
        val myThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                    val it = Intent(applicationContext, MainActivity::class.java)
                    startActivity(it)
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        myThread.start()
    }
}