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

package cn.govast.city.db

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cn.govast.city.model.Area
import kotlinx.coroutines.flow.Flow

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

@Dao
interface AreaDao {

    /**
     * 向数据库插入区域信息
     */
    @Insert
    suspend fun insertArea(area: Area):Long

    /**
     * 更新区域信息
     */
    @Update
    suspend fun updateArea(vararg area: Area)

    /**
     * 删除区域信息
     */
    @Delete
    fun deleteArea(area: Area)

    /**
     * 根据 [cityCode] 查询区域信息
     */
    @Query("select * from Areas where code = :code")
    fun searchAreaById(code:String): Area

    /**
     * 获取 [Area]
     */
    @Query("select * from Areas")
    fun getAllArea(): Flow<List<Area>>

}