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

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.govast.city.model.Area

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/30
// Description: 
// Documentation:
// Reference:

@Database(entities = [Area::class],version = 1)
abstract class AreaDatabase: RoomDatabase() {

    abstract fun AreaDao(): AreaDao

    companion object{
        private var instance: AreaDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AreaDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context,
                AreaDatabase::class.java,"areas").build().apply {
                instance = this
            }
        }
    }

}