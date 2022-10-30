package cn.govast.vmusic.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import cn.govast.vmusic.BR
import cn.govast.vmusic.model.music.search.Song
import cn.govast.vastadapter.recycleradpter.VastBindListAdapter

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

class SongDiffUtil:DiffUtil.ItemCallback<Song>(){
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }
}

/**
 * [Song] 的适配器
 *
 * @param context
 */
class MusicAdapter(context: Context):VastBindListAdapter<Song>(context,SongDiffUtil()) {
    override fun setVariableId(): Int {
        return BR.song
    }

    /**
     * 通过 [pos] 获取歌曲对象
     *
     * @param pos
     */
    fun getMusicByPos(pos:Int):Song{
        return getItem(pos)
    }
}