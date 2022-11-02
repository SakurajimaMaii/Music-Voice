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

package cn.govast.vmusic.service.musicdownload

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.govast.vasttools.broadcastreceiver.VastBroadcastReceiver
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.filemgr.FileMgr
import cn.govast.vasttools.service.VastService
import cn.govast.vasttools.utils.DownloadUtils
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.utils.ResUtils
import cn.govast.vasttools.utils.ToastUtils
import cn.govast.vmusic.R
import cn.govast.vmusic.broadcast.BConstant
import cn.govast.vmusic.ui.activity.MusicActivity

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/2
// Description: 
// Documentation:
// Reference:

class MusicDownloadService : VastService() {

    inner class DownloadBroadcast : VastBroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val downloadInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.extras?.getSerializable(
                    BConstant.DOWNLOAD_KEY,
                    MusicActivity.DownloadInfo::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                cast<MusicActivity.DownloadInfo>(intent?.extras?.getSerializable(BConstant.DOWNLOAD_KEY))
            }
            downloadInfo?.also { info ->
                FileMgr.appInternalFilesDir().path.also {
                    DownloadUtils
                        .createConfig()
                        .setDownloadUrl(info.url)
                        .setSaveDir(it)
                        .setSaveName("${info.name}.mp3")
                        .setDownloading { progressInfo ->
                            LogUtils.i(getDefaultTag(), "${info.name} ${progressInfo?.percent}")
                        }
                        .setDownloadFailed { ex ->
                            ToastUtils.showShortMsg("${ResUtils.getString(R.string.err_info_download)} ${ex?.message}")
                        }
                        .setDownloadSuccess {
                            // 需要通知 contentResolver,插入一条信息
                            val values = ContentValues().apply {
                                put(MediaStore.Audio.Media.TITLE, info.name)
                                put(MediaStore.Audio.Media.DATA, it)
                                put(MediaStore.Audio.Media.ARTIST, info.name)
                            }
                            contentResolver.insert(
                                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                values
                            )
                            ToastUtils.showShortMsg(ResUtils.getString(R.string.cor_info_download_success))
                        }
                        .download()
                } ?: ToastUtils.showShortMsg(ResUtils.getString(R.string.err_info_download_path))
            } ?: ToastUtils.showShortMsg(ResUtils.getString(R.string.err_info_download_info))
        }

    }

    private val mDownloadBroadcast by lazy {
        DownloadBroadcast()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mDownloadBroadcast, IntentFilter(BConstant.ACTION_DOWNLOAD))
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDownloadBroadcast)
    }

}