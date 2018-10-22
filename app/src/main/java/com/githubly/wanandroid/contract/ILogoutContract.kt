package com.githubly.wanandroid.contract

import com.githubly.wanandroid.presenter.base.IView

/**
 * 类名：ILogoutContract
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 16:39
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface ILogoutContract {

    interface Presenter{

        fun logout()
    }

    interface View:IView{
        fun onLogoutSuccess()
        fun onLogoutFailed(msg:String)
    }
}