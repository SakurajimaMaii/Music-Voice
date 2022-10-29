package cn.govast.gmusic.model.music.search

data class Result(
    val searchQcReminder: Any,
    val songCount: Int,
    val songs: List<Song>
)