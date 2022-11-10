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

import cn.govast.vasttools.lifecycle.StateLiveData
import cn.govast.vasttools.viewModel.VastViewModel
import cn.govast.vmusic.model.net.toplist.TopList
import cn.govast.vmusic.network.repository.MusicRepository


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/10
// Description: 
// Documentation:
// Reference:

class RankingVM: VastViewModel() {

    val topList = StateLiveData<TopList>()

    fun getTopList(){
        getRequestBuilder()
            .suspendWithListener({ MusicRepository.getTopList() }){
                onSuccess = {
                    topList.postValueAndSuccess(it)
                }
            }
    }

}