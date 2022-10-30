package cn.govast.vmusic.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import cn.govast.vmusic.extension.toFields
import cn.govast.vmusic.model.user.UserProfile
import cn.govast.vasttools.helper.ContextHelper

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/28
// Description: 
// Documentation:
// Reference:

object UserSp {
    private const val UserSpFile = "UserSpFile"
    private val mContext by lazy {
        ContextHelper.getAppContext()
    }
    private val mSharedPreferences by lazy {
        mContext.getSharedPreferences(UserSpFile, Context.MODE_PRIVATE)
    }

    fun getSp(): SharedPreferences {
        return mSharedPreferences
    }

    fun writeUser(userProfile: UserProfile) {
        with(mSharedPreferences.edit()) {
            userProfile.toFields().forEach { (name, value) ->
                when (value) {
                    is Double -> putLong(name, value.toRawBits())
                    is Boolean -> putBoolean(name, value)
                    is String -> putString(name, value)
                }
            }
            apply()
        }
    }

}