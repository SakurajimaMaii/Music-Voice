package cn.govast.gmusic.network.service

import cn.govast.gmusic.model.music.play.MusicUrl
import cn.govast.gmusic.model.music.search.MusicSearch
import retrofit2.http.POST
import retrofit2.http.Query

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

interface MusicService {
    /**
     * [根据name搜索歌曲](https://neteasecloudmusicapi.vercel.app/#/?id=%e6%90%9c%e7%b4%a2)
     *
     * @param name
     * @return
     */
    @POST("/cloudsearch")
    suspend fun searchMusic(@Query("keywords") name:String): MusicSearch

    /**
     * [获取音乐Url](https://neteasecloudmusicapi.vercel.app/#/?id=%e8%8e%b7%e5%8f%96%e9%9f%b3%e4%b9%90-url-%e6%96%b0%e7%89%88)
     *
     * @param id 音乐id
     * @param level 播放等级
     */
    @POST("/song/url/v1")
    suspend fun getMusicUrl(@Query("id") id:Int,@Query("level") level:String): MusicUrl
}