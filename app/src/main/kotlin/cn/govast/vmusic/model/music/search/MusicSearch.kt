package cn.govast.vmusic.model.music.search

import cn.govast.vasttools.network.base.BaseApiRsp

data class MusicSearch(
    val code: Int,
    val result: Result
):BaseApiRsp