package com.githubly.wanandroid

import android.app.Application
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.githubly.wanandroid.model.User
import com.githubly.wanandroid.utils.UserInfoHelper
import kotlin.properties.Delegates

/**
 * 类名：App
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 10:05
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class App : Application() {

    companion object {
        var instance: App by Delegates.notNull()        //实例用它
        var mAppContext: Application by Delegates.notNull()     //applicationContext用它
    }

    var user: User? = null
        set(value) {
            field = value
            if (value != null) {
                UserInfoHelper.saveUser(value)
            } else {
                UserInfoHelper.clearUser()
            }
        }
        get() {
            if (field == null)
                field = UserInfoHelper.getUser()
            return field
        }

    var isLogin: Boolean = false
        get() {
            field = user != null
            return field
        }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mAppContext = this
        UserInfoHelper.init(this)
        BGASwipeBackHelper.init(this, null)
    }

}