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