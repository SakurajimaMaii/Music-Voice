package cn.govast.gmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.govast.gmusic.model.music.search.Song
import cn.govast.vasttools.viewModel.VastViewModel
import java.io.Serializable

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 14:48
// Description:
// Documentation:

class MainSharedVM : VastViewModel() {

    data class MusicInfo(val name: String, val albumName: String, val albumUrl: String):
        Serializable

    /** 当前页面的Music */
    private val _mCurrentMusic = MutableLiveData<MusicInfo>()
    val mCurrentMusic: LiveData<MusicInfo>
        get() = _mCurrentMusic

    /**
     * 音乐播放列表
     */
    private val _mSongList = MutableLiveData<List<Song>>()
    val mSongList: LiveData<List<Song>>
        get() = _mSongList

    /**
     * 当前播放的进度
     */
    var currentProgress:Float = 0f

    /** 设置 [cn.govast.gmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
    fun setCurrentMusic(song: Song) {
        _mCurrentMusic.postValue(MusicInfo(song.name, song.album.name, song.album.getPicUrl()))
    }

    /** 设置 [cn.govast.gmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
    fun setCurrentMusic(index:Int){
        _mSongList.value?.get(index)?.apply {
            setCurrentMusic(this)
        }
    }

    /**
     * 更新播放列表
     *
     * @param list
     */
    fun updateMusicList(list:List<Song>){
        _mSongList.postValue(list)
    }

}