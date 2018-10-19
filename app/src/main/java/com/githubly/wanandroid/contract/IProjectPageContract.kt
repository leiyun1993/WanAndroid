package com.githubly.wanandroid.contract

import com.githubly.wanandroid.model.ArticleItem
import com.githubly.wanandroid.model.BaseListData
import com.githubly.wanandroid.presenter.base.IView

/**
 * 类名：IProjectPageContract
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 17:11
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface IProjectPageContract {

    interface Presenter{
        fun getProjectArticle(userID:Int,page:Int)
    }

    interface View:IView{
        fun onArticleSuccess(data: BaseListData<ArticleItem>)
        fun onArticleFailed(msg: String)
    }
}