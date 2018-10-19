package com.githubly.wanandroid.presenter

import com.githubly.wanandroid.contract.IArticlePageContract
import com.githubly.wanandroid.net.ApiCallBack
import com.githubly.wanandroid.net.ApiHelper
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：ArticlePagePresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 17:12
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ArticlePagePresenter(view: IArticlePageContract.View) : BasePresenter<IArticlePageContract.View>(view), IArticlePageContract.Presenter {

    override fun getWXArticle(userID: Int, page: Int) {
        ApiHelper.api.getWXArticle(userID, page).enqueue(ApiCallBack {
            if (isSuccess) {
                mView?.onArticleSuccess(data!!)
            } else {
                mView?.onArticleFailed(errorMsg)
            }
        })
    }
}