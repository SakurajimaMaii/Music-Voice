package cn.govast.vmusic.model.music.search

/**
 * 歌曲艺术家信息
 *
 * @property alia
 * @property alias
 * @property id
 * @property name
 * @property tns
 */
data class Artist(
    val alia: List<String>,
    val alias: List<String>,
    val id: Int,
    val name: String,
    val tns: List<Any>
)