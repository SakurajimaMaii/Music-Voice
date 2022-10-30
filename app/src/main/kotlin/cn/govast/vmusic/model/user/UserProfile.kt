package cn.govast.vmusic.model.user

import androidx.room.Entity
import cn.govast.vmusic.extension.AutoField

/**
 * 获取的用户对象
 *
 * 该对象会被存储在数据库中
 *
 * @property accountStatus
 * @property accountType
 * @property anchor
 * @property authStatus
 * @property authenticated
 * @property authenticationTypes
 * @property authority
 * @property avatarImgId
 * @property avatarUrl
 * @property backgroundImgId
 * @property backgroundUrl
 * @property birthday
 * @property city
 * @property createTime
 * @property defaultAvatar
 * @property djStatus
 * @property followed
 * @property gender
 * @property lastLoginIP
 * @property lastLoginTime
 * @property locationStatus
 * @property mutual
 * @property nickname
 * @property province
 * @property shortUserName
 * @property signature
 * @property userId
 * @property userName
 * @property userType
 * @property vipType
 * @property viptypeVersion
 */
@Entity(tableName = "UserProfiles")
class UserProfile(
    @AutoField("accountStatus") val accountStatus: Double,
    @AutoField("accountType") val accountType: Double,
    @AutoField("anchor") val anchor: Boolean,
    @AutoField("authStatus") val authStatus: Double,
    @AutoField("authenticated") val authenticated: Boolean,
    @AutoField("authenticationTypes") val authenticationTypes: Double,
    @AutoField("authority") val authority: Double,
    @AutoField("avatarImgId") val avatarImgId: Double,
    @AutoField("avatarUrl") val avatarUrl: String,
    @AutoField("backgroundImgId") val backgroundImgId: Double,
    @AutoField("backgroundUrl") val backgroundUrl: String,
    @AutoField("birthday") val birthday: Double,
    @AutoField("city") val city: Double,
    @AutoField("createTime") val createTime: Double,
    @AutoField("defaultAvatar") val defaultAvatar: Boolean,
    @AutoField("djStatus") val djStatus: Double,
    @AutoField("followed") val followed: Boolean,
    @AutoField("gender") val gender: Double,
    @AutoField("lastLoginIP") val lastLoginIP: String,
    @AutoField("lastLoginTime") val lastLoginTime: Double,
    @AutoField("locationStatus") val locationStatus: Double,
    @AutoField("mutual") val mutual: Boolean,
    @AutoField("nickname") val nickname: String,
    @AutoField("province") val province: Double,
    @AutoField("shortUserName") val shortUserName: String,
    @AutoField("signature") val signature: String,
    @AutoField("userId") val userId: Double,
    @AutoField("userName") val userName: String,
    @AutoField("userType") val userType: Double,
    @AutoField("vipType") val vipType: Double,
    @AutoField("viptypeVersion") val viptypeVersion: Double
)