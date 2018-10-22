package com.githubly.wanandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import com.githubly.wanandroid.model.User

@SuppressLint("StaticFieldLeak")
/**
 * 类名：UserInfoHelper
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 12:56
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
object UserInfoHelper {

    private var ctx: Context? = null

    fun init(context: Context) {
        ctx = context.applicationContext
    }

    /**
     * 保存用户信息
     */
    fun saveUser(user: User) {
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        sp?.edit()?.apply {
            putString("email", user.email)
            putString("icon", user.icon)
            putString("username", user.username)
            putString("password", user.password)
            putString("token", user.token)
            putInt("id", user.id)
            putInt("type", user.type)
        }?.apply()
    }

    /**
     * 获取保存的用户信息
     */
    fun getUser(): User? {
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val user = User(
                sp?.getString("email", "") ?: "",
                sp?.getString("icon", "") ?: "",
                sp?.getInt("id", -1) ?: -1,
                sp?.getString("password", "") ?: "",
                sp?.getString("token", "") ?: "",
                sp?.getInt("type", -1) ?: -1,
                sp?.getString("username", "") ?: ""

        )
        return if (user.id == -1) null else user
    }

    /**
     * 清除保存的用户信息
     */
    fun clearUser() {
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        sp?.edit()?.clear()?.apply()
    }


    fun saveCookie(set:HashSet<String>){
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        sp?.edit()?.apply {
            putStringSet("cookie",set)
        }?.apply()
    }

    fun getCookie():HashSet<String>?{
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        return sp?.getStringSet("cookie", hashSetOf<String>()) as HashSet<String>?
    }
}