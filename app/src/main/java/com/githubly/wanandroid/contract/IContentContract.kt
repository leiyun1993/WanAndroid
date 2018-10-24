package com.githubly.wanandroid.contract

import com.githubly.wanandroid.presenter.base.IView

/**
 * 类名：IContentContract
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-23 16:13
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface IContentContract {

    interface Presenter {
        fun articleCollect(id: Int)
        fun articleUncollect(id: Int)
        fun articleUncollectPage(id: Int,originId:Int)
    }

    interface View : IView {
        fun onCollectSuccess()
        fun onCollectFailed(msg: String)
        fun onUncollectSuccess()
        fun onUncollectFailed(msg: String)
        fun onUncollectPageSuccess()
        fun onUncollectPageFailed(msg: String)
    }

}