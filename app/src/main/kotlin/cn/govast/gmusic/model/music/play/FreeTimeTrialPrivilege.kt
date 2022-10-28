package cn.govast.gmusic.model.music.play

data class FreeTimeTrialPrivilege(
    val remainTime: Int,
    val resConsumable: Boolean,
    val type: Int,
    val userConsumable: Boolean
)