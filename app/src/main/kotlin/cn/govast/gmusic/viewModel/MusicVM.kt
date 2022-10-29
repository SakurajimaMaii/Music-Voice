package cn.govast.gmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.govast.gmusic.model.music.search.Song
import cn.govast.vasttools.viewModel.VastViewModel

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

class MusicVM : VastViewModel() {

    /** 当前页面的Music */
    private val _mCurrentMusic = MutableLiveData<MainSharedVM.MusicInfo>()

    val mCurrentMusic: LiveData<MainSharedVM.MusicInfo>
        get() = _mCurrentMusic

    /** 设置 [cn.govast.gmusic.databinding.ActivityMusicBinding] 页面的音乐信息 */
    fun setCurrentMusic(song: Song) {
        _mCurrentMusic.postValue(
            MainSharedVM.MusicInfo(
                song.name,
                song.album.name,
                song.album.getPicUrl()
            )
        )
    }

    /** 设置 [cn.govast.gmusic.databinding.ActivityMusicBinding] 页面的音乐信息 */
    fun setCurrentMusic(musicInfo: MainSharedVM.MusicInfo) {
        _mCurrentMusic.postValue(musicInfo)
    }
}