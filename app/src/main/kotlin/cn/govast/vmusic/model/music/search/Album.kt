package cn.govast.vmusic.model.music.search

/**
 * 歌曲专辑信息
 *
 * @property id
 * @property name
 * @property pic
 * @property picUrl 专辑照片链接
 * @property pic_str
 * @property tns
 */
data class Album(
    val id: Int,
    val name: String,
    val pic: Long,
    private val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
){
    fun getPicUrl() = picUrl.replace("http:","https:")
}