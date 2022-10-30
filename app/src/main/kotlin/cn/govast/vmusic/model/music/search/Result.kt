package cn.govast.vmusic.model.music.search

data class Result(
    val searchQcReminder: Any,
    val songCount: Int,
    val songs: List<Song>
)