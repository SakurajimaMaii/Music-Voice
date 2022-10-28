package cn.govast.gmusic.model.music.search

data class Result(
    val hasMore: Boolean,
    val songCount: Int,
    val songs: List<Song>
)