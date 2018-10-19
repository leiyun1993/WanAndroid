package com.githubly.wanandroid.presenter

import com.githubly.wanandroid.contract.IProjectPageContract
import com.githubly.wanandroid.net.ApiCallBack
import com.githubly.wanandroid.net.ApiHelper
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：ProjectPagePresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 17:12
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ProjectPagePresenter(view: IProjectPageContract.View) : BasePresenter<IProjectPageContract.View>(view), IProjectPageContract.Presenter {


    override fun getProjectArticle(userID: Int, page: Int) {
        if (userID == 99999) {
            //接口兼容   page 1  index = 0
            ApiHelper.api.getLatestProjects(page-1).enqueue(ApiCallBack {
                if (isSuccess) {
                    mView?.onArticleSuccess(data!!)
                } else {
                    mView?.onArticleFailed(errorMsg)
                }
            })
        } else {
            //接口兼容   page 1  index = 0
            ApiHelper.api.getProjects(page-1, userID).enqueue(ApiCallBack {
                if (isSuccess) {
                    mView?.onArticleSuccess(data!!)
                } else {
                    mView?.onArticleFailed(errorMsg)
                }
            })
        }
    }


}