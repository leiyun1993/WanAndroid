package com.githubly.wanandroid.contract

import com.githubly.wanandroid.model.ArticleItem
import com.githubly.wanandroid.model.Banner
import com.githubly.wanandroid.model.BaseListData
import com.githubly.wanandroid.presenter.base.IView

/**
 * 类名：IHomeContract
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 11:33
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface IHomeContract {

    interface Presenter {
        fun getBanner()
        fun getHomeArticle(page:Int)
    }

    interface View : IView {
        fun onBannerSuccess(banners: MutableList<Banner>)
        fun onBannerFailed(msg: String)
        fun onArticleSuccess(data:BaseListData<ArticleItem>)
        fun onArticleFailed(msg: String)
    }
}