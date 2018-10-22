package com.githubly.wanandroid.contract

import com.githubly.wanandroid.model.ArticleItem
import com.githubly.wanandroid.model.BaseListData
import com.githubly.wanandroid.presenter.base.IView

/**
 * 类名：ICollectListContract
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 17:46
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface ICollectListContract {

    interface Presenter{
        fun getCollectList(page:Int)
    }

    interface View:IView{
        fun onListSuccess(data: BaseListData<ArticleItem>)
        fun onListFailed(msg:String)
    }

}