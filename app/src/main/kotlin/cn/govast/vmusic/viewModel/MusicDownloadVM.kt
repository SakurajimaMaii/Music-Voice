/*
 * Copyright 2022 Vast Gui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.govast.vmusic.viewModel

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.govast.vasttools.helper.ContextHelper
import cn.govast.vasttools.utils.AppUtils
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vasttools.viewModel.VastViewModel
import cn.govast.vmusic.model.LocalMusic

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/1
// Description: 
// Documentation:
// Reference:

class MusicDownloadVM : VastViewModel() {

    /** 当前下载的音乐 */
    private val _mCurrentDownloadList = MutableLiveData<MutableList<LocalMusic>>()
    val mCurrentDownloadList: LiveData<MutableList<LocalMusic>>
        get() = _mCurrentDownloadList

    @SuppressLint("Range")
    fun loadLocalMusicData() {
        val cursor: Cursor? = ContextHelper.getAppContext().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Audio.Media.ARTIST + "!=?",
            arrayOf("<unknown>"),
            null
        )
        when {
            cursor == null -> {
                LogUtils.e(getDefaultTag(), "查询失败，处理错误。")
            }

            !cursor.moveToFirst() -> {
                ToastUtils.showShortMsg("设备上没有歌曲")
            }

            else -> {
                val list = ArrayList<LocalMusic>()
                do {
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    var singer: String? = null
                    var album: String? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        singer =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                        album =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    } else {
                        Log.w(
                            getDefaultTag(),
                            "当前版本的手机不支持查找singer和album,MIN VERSION_CODE R"
                        )
                    }
                    var duration: Long? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        duration =
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    } else {
                        Log.w(
                            getDefaultTag(),
                            "当前版本的手机不支持查找singer和album,MIN VERSION_CODE Q"
                        )
                    }
                    //将一行当中的对象封装到数据当中
                    val bean = LocalMusic(song, singer ?: "", album ?: "", duration ?: 0L, path)
                    list.add(bean)
                } while (cursor.moveToNext())
                _mCurrentDownloadList.postValue(list)
            }
        }
        cursor?.close()
    }

}