package com.githubly.wanandroid.contract

import com.githubly.wanandroid.model.User
import com.githubly.wanandroid.presenter.base.IView

/**
 * 类名：ILoginContract
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 14:43
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface ILoginContract {

    interface Presenter {
        fun login(userName: String, password: String)
        fun register(userName: String, password: String, rePassword: String)
    }

    interface View : IView {
        fun onLoginSuccess(user: User)
        fun onLoginFailed(msg: String)

        fun onRegisterSuccess(user: User)
        fun onRegisterFailed(msg: String)
    }
}