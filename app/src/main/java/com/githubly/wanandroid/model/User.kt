package com.githubly.wanandroid.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * 类名：User
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 12:51
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

data class User(
        @SerializedName("email") var email: String = "",
        @SerializedName("icon") var icon: String = "",
        @SerializedName("id") var id: Int = 0,
        @SerializedName("password") var password: String = "",
        @SerializedName("token") var token: String = "",
        @SerializedName("type") var type: Int = 0,
        @SerializedName("username") var username: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(icon)
        parcel.writeInt(id)
        parcel.writeString(password)
        parcel.writeString(token)
        parcel.writeInt(type)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}