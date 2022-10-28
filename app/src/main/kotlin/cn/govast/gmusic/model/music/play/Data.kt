package cn.govast.gmusic.model.music.play

data class Data(
    val br: Int,
    val canExtend: Boolean,
    val code: Int,
    val effectTypes: Any,
    val encodeType: String,
    val expi: Int,
    val fee: Int,
    val flag: Int,
    val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
    val freeTrialInfo: Any,
    val freeTrialPrivilege: FreeTrialPrivilege,
    val gain: Double,
    val id: Int,
    val level: String,
    val md5: String,
    val payed: Int,
    val podcastCtrp: Any,
    val rightSource: Int,
    val size: Int,
    val time: Int,
    val type: String,
    val uf: Any,
    private val url: String,
    val urlSource: Int
){
    fun getUrl() = url.replace("http", "https")
}