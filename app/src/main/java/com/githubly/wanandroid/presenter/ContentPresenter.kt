package com.githubly.wanandroid.presenter

import com.githubly.wanandroid.contract.IContentContract
import com.githubly.wanandroid.net.ApiCallBack
import com.githubly.wanandroid.net.ApiHelper
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：ContentPresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-23 16:16
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ContentPresenter(view: IContentContract.View) :
        BasePresenter<IContentContract.View>(view), IContentContract.Presenter {
    override fun articleCollect(id: Int) {
        ApiHelper.api.collect(id).enqueue(ApiCallBack {
            if (isSuccess) {
                mView?.onCollectSuccess()
            } else {
                mView?.onCollectFailed(errorMsg)
            }
        })
    }

    override fun articleUncollect(id: Int) {
        ApiHelper.api.uncollect(id).enqueue(ApiCallBack{
            if (isSuccess) {
                mView?.onUncollectSuccess()
            } else {
                mView?.onUncollectFailed(errorMsg)
            }
        })
    }

    override fun articleUncollectPage(id: Int,originId:Int) {
        ApiHelper.api.unCollectPage(id,originId).enqueue(ApiCallBack{
            if (isSuccess) {
                mView?.onUncollectPageSuccess()
            } else {
                mView?.onUncollectPageFailed(errorMsg)
            }
        })
    }
}