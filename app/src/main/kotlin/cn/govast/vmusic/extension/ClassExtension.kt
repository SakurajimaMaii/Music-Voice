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

package cn.govast.vmusic.extension

import java.lang.reflect.Field


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference: https://www.jianshu.com/p/427f24f74886

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoEnumValue(val value: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoField(val value: String = "")

inline fun <reified T> T.toFields(): HashMap<String,Any>{
    val handlerType: Class<T> = T::class.java
    val clazz = handlerType.superclass
    val fields = handlerType.declaredFields
    val superfields = clazz.declaredFields
    if (fields.isNotEmpty()) {
        Field.setAccessible(fields, true)
        Field.setAccessible(superfields, true)
        val parts = HashMap<String,Any>()
        for (field in fields) {
            try {
                val autoField = field.getAnnotation(AutoField::class.java)
                if (autoField != null) {
                    val value = field.get(this) ?: continue
                    val key = autoField.value.ifEmpty { field.name }
                    if (value is Enum<*>) {
                        //如果对象是枚举
                        val declaredFields = value.javaClass.declaredFields
                        Field.setAccessible(declaredFields, true)
                        for (enumField in declaredFields) {
                            val annotation = enumField.getAnnotation(AutoEnumValue::class.java)
                            if (annotation != null) {
                                val enumValue = enumField.get(value)
                                if (enumValue != null) {
                                    parts[key] = enumValue
                                    break
                                }
                            }
                        }
                    } else {
                        parts[key] = value
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        for (field in superfields) {
            try {
                val autoField = field.getAnnotation(AutoField::class.java)
                if (autoField != null) {
                    val value = field.get(this) ?: continue
                    val key = autoField.value.ifEmpty { field.name }
                    if (value is Enum<*>) {
                        //如果对象是枚举
                        val declaredFields = value.javaClass.declaredFields
                        Field.setAccessible(declaredFields, true)
                        for (enumField in declaredFields) {
                            val annotation = enumField.getAnnotation(AutoEnumValue::class.java)
                            if (annotation != null) {
                                val enumValue = enumField.get(value)
                                if (enumValue != null) {
                                    parts[key] = enumValue
                                    break
                                }
                            }
                        }
                    } else {
                        parts[key] = value
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return parts
    }
    return HashMap()
}