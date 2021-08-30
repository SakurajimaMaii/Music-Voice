package com.gcode.gmusic.manager

import android.content.ContentUris
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.provider.MediaStore
import android.util.Log
import com.gcode.gmusic.utils.AppUtils
import java.io.IOException

/**
 *作者:created by HP on 2021/7/3 14:28
 *邮箱:sakurajimamai2020@qq.com
 */

/**
 * 音乐播放控制器
 */
object PlayManager {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var isFirstRun:Boolean

    init {
        isFirstRun = false
    }

    fun playMusicInMusicBean(musicId: Long) {
        stopMusic()
        //重置多媒体播放器
        mediaPlayer.reset()
        //设置新的播放路径
        try {
            mediaPlayer.apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(
                    AppUtils.context,
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicId)
                )
            }
            playMusic()
        } catch (e: IOException) {
            Log.e(this.javaClass.simpleName, "Play error,function execution error",e)
            e.printStackTrace()
        }
    }

    fun playMusic() {
        //播放音乐的函数
        if (!mediaPlayer.isPlaying) {
            if (mediaPlayer.currentPosition == 0) {
                try {
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                //从暂停到播放
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
            }
        }
    }

    fun pauseMusic() {
        /* 暂停音乐的函数*/
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    /**
     * 停止音乐的函数
     * 在这里加入isFirstRun的主要用意如下:
     * 在首次启动软件时,如果第一次点击音乐,执行playMusicInMusicBean()时,进行到stopMusic()函数时,在不加isFirstRun判定条件的情况下,
     * 直接执行mediaPlayer.pause();会报错,诸如 E/MediaPlayerNative: pause called in state 1, mPlayer(0x0),那是因为不符合MediaPlayer生命周期,
     * 因而在加入isFirstRun条件,在初次运行时并不执行if条件句里面的内容,之后将isFirstRun设置为true
     */
    fun stopMusic() {
        if (isFirstRun) {
            Log.d(this.javaClass.simpleName, "stopMusic()")
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
            mediaPlayer.stop()
        } else {
            isFirstRun = true
        }
    }

    /**
     * 是否在播放
     * @return Boolean
     */
    fun isPlaying() = mediaPlayer.isPlaying
}