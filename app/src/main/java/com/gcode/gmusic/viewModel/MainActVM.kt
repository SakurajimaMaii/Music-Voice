package com.gcode.gmusic.viewModel

import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gcode.gmusic.model.MusicBean
import com.gcode.gmusic.utils.AppUtils
import com.gcode.tools.adapter.BaseUtilItem
import com.gcode.tools.utils.MsgWindowUtils
import java.util.ArrayList

/**
 *作者:created by HP on 2021/7/3 14:48
 *邮箱:sakurajimamai2020@qq.com
 */
class MainActVM:ViewModel() {
    companion object{
        const val VMTag = "MainActVM"
    }

    //用于查找数据
    private val cacheMusicData:MutableList<MusicBean> = ArrayList()

    //歌曲数据源
    private val localMusicData: MutableLiveData<MutableList<BaseUtilItem>> by lazy {
        MutableLiveData<MutableList<BaseUtilItem>>().also {
            it.postValue(loadLocalMusicData())
        }
    }

    fun getLocalMusicData():LiveData<MutableList<BaseUtilItem>>{
        return localMusicData
    }

    //加载本地歌曲
    private fun loadLocalMusicData() = ArrayList<BaseUtilItem>().also {
        val cursor: Cursor? = AppUtils.context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Audio.Media.ARTIST+"!=?",
            arrayOf("<unknown>"),
            null
        )
        when {
            cursor == null -> {
                Log.e(VMTag, "query failed, handle error.")
            }
            !cursor.moveToFirst() -> {
                MsgWindowUtils.showShortMsg(AppUtils.context, "设备上没有歌曲")
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
                        Log.w(VMTag, "当前版本的手机不支持查找singer和album,MIN VERSION_CODE R")
                    }
                    var duration: Long? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        duration =
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    } else {
                        Log.w(VMTag, "当前版本的手机不支持查找singer和album,MIN VERSION_CODE Q")
                    }
                    //将一行当中的对象封装到数据当中
                    val bean = MusicBean(id, song, singer, album, duration)
                    cacheMusicData.add(bean)
                    it.add(bean)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
    }

    private val _currentPlayPos:MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(-1)
    }

    val currentPlayPos:LiveData<Int>
        get() = _currentPlayPos

    fun setCurrentPlayPos(currentPlayPosition:Int){
        this._currentPlayPos.postValue(currentPlayPosition)
    }

    private val _currentPlayMusic:MutableLiveData<MusicBean> by lazy {
        MutableLiveData<MusicBean>()
    }

    val currentPlayMusic:LiveData<MusicBean>
        get() = _currentPlayMusic

    fun setCurrentPlayMusic(music: MusicBean){
        this._currentPlayMusic.postValue(music)
    }

    fun getMusicPyPos(pos:Int) = cacheMusicData[pos]

    fun getMusicCount() = cacheMusicData.size
}