package cn.govast.gmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.govast.gmusic.model.music.play.MusicQuality
import cn.govast.gmusic.model.music.play.MusicUrl
import cn.govast.gmusic.model.music.search.MusicSearch
import cn.govast.gmusic.model.music.search.Song
import cn.govast.gmusic.network.repository.MusicRepository
import cn.govast.music.MusicPlayState
import cn.govast.music.MusicPlayer
import cn.govast.vasttools.helper.ContextHelper
import cn.govast.vasttools.livedata.NetStateLiveData
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.viewModel.VastViewModel

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/7/3 14:48
// Description:
// Documentation:

class MainSharedVM : VastViewModel() {

    data class MusicInfo(val name: String, val albumName: String, val albumUrl: String)

    /** 音乐播放器 */
    private val mMusicPlayer by lazy {
        MusicPlayer(ContextHelper.getAppContext())
    }

    /** 当前页面的Music */
    private val _mCurrentMusic = MutableLiveData<MusicInfo>()

    val mCurrentMusic: LiveData<MusicInfo>
        get() = _mCurrentMusic

    /** 音乐搜索结果 */
    val mMusicSearch = NetStateLiveData<MusicSearch>()

    /** 音乐Url */
    val mMusicUrl = NetStateLiveData<MusicUrl>()

    /**
     * 音乐播放状态
     */
    private val _mPlayState = MutableLiveData<MusicPlayState>()
    val mPlayState:LiveData<MusicPlayState>
        get() = _mPlayState

    /** 播放音乐 */
    fun playMusic() {
        if (mMusicPlayer.isPlaying()) {
            mMusicPlayer.pauseMusic()
            _mPlayState.postValue(MusicPlayState.PAUSE)
        } else {
            mMusicPlayer.playMusic()
            _mPlayState.postValue(MusicPlayState.PLAY)
        }
    }

    fun searchMusic(name: String) {
        getRequestBuilder()
            .suspendWithListener({ MusicRepository.searchMusic(name) }) {
                onSuccess = {
                    mMusicSearch.postValueAndSuccess(it)
                }
                onError = {
                    LogUtils.e(getDefaultTag(), it?.message ?: "歌曲搜索失败")
                }
            }
    }

    /** 设置 [cn.govast.gmusic.databinding.ActivityMainBinding] 页面的音乐信息 */
    fun setCurrentMusic(song: Song) {
        _mCurrentMusic.postValue(MusicInfo(song.name, song.album.name, song.album.artist.img1v1Url))
    }

    /**
     * 获取音乐Url
     *
     * @param id
     * @param quality
     */
    fun getMusicUrl(id: Int, quality: MusicQuality) {
        getRequestBuilder()
            .suspendWithListener({ MusicRepository.getMusicUrl(id, quality) }) {
                onSuccess = {
                    mMusicPlayer.playMusic(it.data[0].getUrl())
                    _mPlayState.postValue(MusicPlayState.PLAY)
                }
            }
    }
}