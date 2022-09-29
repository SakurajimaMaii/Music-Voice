package cn.govast.gmusic.viewModel

import android.annotation.SuppressLint
import android.content.ContentUris
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.govast.gmusic.model.MusicBean
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.helper.ContextHelper
import cn.govast.vasttools.utils.ToastUtils
import com.gcode.vastadapter.interfaces.VastBindAdapterItem
import java.io.IOException

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 14:48
// Description:
// Documentation:

class MainActVM:ViewModel() {
    private val tag = this.javaClass.simpleName

    //用于查找数据
    private val cacheMusicData:MutableList<MusicBean> = ArrayList()

    //歌曲数据源
    private val localMusicData: MutableLiveData<MutableList<VastBindAdapterItem>> by lazy {
        MutableLiveData<MutableList<VastBindAdapterItem>>().also {
            it.postValue(loadLocalMusicData())
        }
    }

    /**
     * 当前播放的歌曲位置
     */
    var currentPlayPos:Int = -1
        private set

    //主要用于Fragment向Activity传递当前播放的歌曲
    private val _currentPlayMusic:MutableLiveData<VastBindAdapterItem> by lazy {
        MutableLiveData<VastBindAdapterItem>()
    }

    val currentPlayMusic:LiveData<VastBindAdapterItem>
        get() = _currentPlayMusic

    /**
     * 媒体播放器,以下是相关的方法
     */
    private val mediaPlayer:MediaPlayer = MediaPlayer()

    fun getLocalMusicData():LiveData<MutableList<VastBindAdapterItem>>{
        return localMusicData
    }

    //加载本地歌曲
    @SuppressLint("Range")
    private fun loadLocalMusicData() = ArrayList<VastBindAdapterItem>().also {
        val cursor: Cursor? = ContextHelper.getAppContext().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Audio.Media.ARTIST+"!=?",
            arrayOf("<unknown>"),
            null
        )
        when {
            cursor == null -> {
                Log.e(tag, "query failed, handle error.")
            }
            !cursor.moveToFirst() -> {
                ToastUtils.showShortMsg(ContextHelper.getAppContext(), "设备上没有歌曲")
            }
            else -> {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    var singer: String? = null
                    var album: String? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        singer =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                        album =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    } else {
                        Log.w(tag, "当前版本的手机不支持查找singer和album,MIN VERSION_CODE R")
                    }
                    var duration: Long? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        duration =
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    } else {
                        Log.w(tag, "当前版本的手机不支持查找singer和album,MIN VERSION_CODE Q")
                    }
                    //将一行当中的对象封装到数据当中
                    val bean = MusicBean(id, song, singer, album, duration)
                    cacheMusicData.add(bean)
                    it.add(bean)
                } while (cursor.moveToNext())
            }
        }
        //初始化后将第一首歌作为等待播放歌曲
        initMediaPlayer(it[0])
        cursor?.close()
    }

    fun setCurrentPlayPos(currentPlayPosition:Int){
        currentPlayPos = currentPlayPosition
    }

    private fun setCurrentPlayMusic(music: MusicBean){
        this._currentPlayMusic.postValue(music)
    }

    fun getMusicPyPos(pos:Int) = cacheMusicData[pos]

    fun getMusicCount() = cacheMusicData.size

    fun playMusicInMusicBean(music: MusicBean) {
        setCurrentPlayMusic(music)
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
                    ContextHelper.getAppContext(),
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, music.id.toLong())
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

    fun releaseMediaPlayer(){
        mediaPlayer.release()
    }

    /**
     * 是否在播放
     * @return Boolean
     */
    fun isPlaying() = mediaPlayer.isPlaying

    /**
     * 此函数在获取歌曲结束后必须执行
     * @param music MusicBean
     *
     * initMediaPlayer()函数的主要用意如下:
     * 在首次启动软件时,如果第一次点击音乐,执行playMusicInMusicBean()时,进行到stopMusic()函数时,在不执行initMediaPlayer()的情况下,
     * 直接执行mediaPlayer.pause();会报错,诸如 E/MediaPlayerNative: pause called in state 1, mPlayer(0x0),那是因为不符合MediaPlayer生命周期
     *
     * 关于MediaPlayer生命周期参考
     * https://blog.csdn.net/ddna/article/details/5178864
     */
    private fun initMediaPlayer(music: VastBindAdapterItem){
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(
                ContextHelper.getAppContext(),
                ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cast<MusicBean>(music).id.toLong())
            )
            prepare()
            start()
            stop()
        }
        setCurrentPlayPos(0)
        _currentPlayMusic.postValue(music)
    }
}