package com.githubly.wanandroid.presenter

import com.githubly.wanandroid.contract.ILoginContract
import com.githubly.wanandroid.net.ApiCallBack
import com.githubly.wanandroid.net.ApiHelper
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：LoginPresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 14:44
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class LoginPresenter(view: ILoginContract.View) :
    BasePresenter<ILoginContract.View>(view), ILoginContract.Presenter {
    override fun register(userName: String, password: String, rePassword: String) {
        ApiHelper.api.register(userName, password, rePassword).enqueue(ApiCallBack {
            if (isSuccess) {
                mView?.onRegisterSuccess(data!!)
            } else {
                mView?.onRegisterFailed(errorMsg)
            }
        })
    }

    override fun login(userName: String, password: String) {
        ApiHelper.api.login(userName, password).enqueue(ApiCallBack {
            if (isSuccess) {
                mView?.onLoginSuccess(data!!)
            } else {
                mView?.onLoginFailed(errorMsg)
            }

        })
    }
}