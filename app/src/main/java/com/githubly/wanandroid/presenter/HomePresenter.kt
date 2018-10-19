package com.githubly.wanandroid.presenter

import com.githubly.wanandroid.contract.IHomeContract
import com.githubly.wanandroid.net.ApiCallBack
import com.githubly.wanandroid.net.ApiHelper
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：HomePresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 11:32
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class HomePresenter(view: IHomeContract.View) :
        BasePresenter<IHomeContract.View>(view), IHomeContract.Presenter {
    override fun getHomeArticle(page: Int) {
        //page 从1开始   接口Index 从0开始
        ApiHelper.api.getHomeArticles(page-1).enqueue(ApiCallBack {
            if (isSuccess) {
                mView?.onArticleSuccess(data!!)
            } else {
                mView?.onArticleFailed(errorMsg)
            }
        })
    }

    override fun getBanner() {
        ApiHelper.api.getBanner().enqueue(ApiCallBack {
            if (isSuccess) {
                mView?.onBannerSuccess(data ?: mutableListOf())
            } else {
                mView?.onBannerFailed(errorMsg)
            }
        })
    }

}