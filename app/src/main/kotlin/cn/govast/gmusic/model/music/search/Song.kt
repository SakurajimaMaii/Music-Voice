package cn.govast.gmusic.model.music.search

import cn.govast.gmusic.R
import cn.govast.gmusic.utils.TimeUtils
import cn.govast.vastadapter.AdapterItem

/**
 * 音乐搜索结果返回的音乐对象
 * 请求示例如上
 *
 *
 * @property album
 * @property alias
 * @property artists
 * @property copyrightId
 * @property duration
 * @property fee
 * @property ftype
 * @property id
 * @property mark
 * @property mvid
 * @property name
 * @property rUrl
 * @property rtype
 * @property status
 * @property transNames
 */
data class Song(
    val album: Album,
    val alias: List<String>,
    val artists: List<ArtistX>,
    val copyrightId: Int,
    private val duration: Int,
    val fee: Int,
    val ftype: Int,
    val id: Int,
    val mark: Long,
    val mvid: Int,
    val name: String,
    val rUrl: Any,
    val rtype: Int,
    val status: Int,
    val transNames: List<String>
):AdapterItem{

    fun getDuration() = TimeUtils.timeParse(duration.toLong())

    override fun getBindType(): Int {
        return R.layout.rv_item_music
    }
}