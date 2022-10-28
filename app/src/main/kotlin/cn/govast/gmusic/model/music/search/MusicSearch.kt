package cn.govast.gmusic.model.music.search

import cn.govast.vasttools.network.base.BaseApiRsp

/**
 * 音乐搜索结果
 *
 * @property code
 * @property result
 */
data class MusicSearch(
    val code: Int,
    val result: Result
):BaseApiRsp