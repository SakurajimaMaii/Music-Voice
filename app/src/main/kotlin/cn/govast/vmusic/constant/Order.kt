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

package cn.govast.vmusic.constant


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

/**
 * 界面向服务发送的控制信号
 *
 * @see [cn.govast.vmusic.service.MusicService]
 */
enum class Order {
    PLAY_OR_PAUSE,
    STOP,
    NEXT,
    PREVIOUS,
    LOOP,
    RANDOM,
    LOOP_ALL,
    /**
     * 数据索引
     * @see [cn.govast.vmusic.service.MusicService.PROGRESS]
     */
    PROGRESS,
    /**
     * 数据索引
     * @see [cn.govast.vmusic.service.MusicService.NOW]
     */
    NOW,
    /**
     * 数据索引
     * @see [cn.govast.vmusic.service.MusicService.NAME]
     */
    SEARCH
}