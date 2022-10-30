package cn.govast.vmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.govast.vmusic.model.music.search.Song
import cn.govast.vasttools.viewModel.VastViewModel
import cn.govast.vmusic.service.MusicService
import java.io.Serializable

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 14:48
// Description:
// Documentation:

class MainSharedVM : VastViewModel() {

    data class MusicInfo(val name: String, val albumName: String, val albumUrl: String):
        Serializable

    enum class ProgressState{
        SHOW,HIDE
    }

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

    /**
     * 当前播放器状态
     */
    var currentPlayState: MusicService.PlayState = MusicService.PlayState.NOPLAYING

    /**
     * 进度条状态
     */
    private val _mProgressState = MutableLiveData<ProgressState>()
    val mProgressState: LiveData<ProgressState>
        get() = _mProgressState

    /** 设置 [cn.govast.vmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
    fun setCurrentMusic(song: Song) {
        _mCurrentMusic.postValue(MusicInfo(song.name, song.album.name, song.album.getPicUrl()))
    }

    /** 设置 [cn.govast.vmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
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

    /**
     * 更新Progress状态
     *
     * @param state
     */
    fun updateProgressState(state: ProgressState){
        _mProgressState.postValue(state)
    }

}