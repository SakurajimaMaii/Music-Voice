package cn.govast.gmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.govast.gmusic.model.music.play.MusicUrl
import cn.govast.gmusic.model.music.search.MusicSearch
import cn.govast.gmusic.model.music.search.Song
import cn.govast.music.MusicPlayState
import cn.govast.vasttools.livedata.NetStateLiveData
import cn.govast.vasttools.viewModel.VastViewModel

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 14:48
// Description:
// Documentation:

class MainSharedVM : VastViewModel() {

    data class MusicInfo(val name: String, val albumName: String, val albumUrl: String)

    /** 当前页面的Music */
    private val _mCurrentMusic = MutableLiveData<MusicInfo>()

    val mCurrentMusic: LiveData<MusicInfo>
        get() = _mCurrentMusic

    /** 音乐搜索结果 */
    val mMusicSearch = NetStateLiveData<MusicSearch>()

    /** 音乐Url */
    val mMusicUrl = NetStateLiveData<MusicUrl>()

    /** 音乐播放状态 */
    private val _mPlayState = MutableLiveData<MusicPlayState>()
    val mPlayState: LiveData<MusicPlayState>
        get() = _mPlayState

    /**
     * 音乐播放列表
     */
    private val _mSongList = MutableLiveData<List<Song>>()
    val mSongList: LiveData<List<Song>>
        get() = _mSongList

    /** 设置 [cn.govast.gmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
    fun setCurrentMusic(song: Song) {
        _mCurrentMusic.postValue(MusicInfo(song.name, song.album.name, song.album.getPicUrl()))
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