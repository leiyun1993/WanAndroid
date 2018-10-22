package com.githubly.wanandroid.presenter

import com.githubly.wanandroid.contract.ILogoutContract
import com.githubly.wanandroid.net.ApiCallBack
import com.githubly.wanandroid.net.ApiHelper
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：LogoutPresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 16:40
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class LogoutPresenter(view: ILogoutContract.View) :
        BasePresenter<ILogoutContract.View>(view),ILogoutContract.Presenter {
    override fun logout() {
        ApiHelper.api.logout().enqueue(ApiCallBack{
            if (isSuccess){
                mView?.onLogoutSuccess()
            }else{
                mView?.onLogoutFailed(errorMsg)
            }
        })
    }
}