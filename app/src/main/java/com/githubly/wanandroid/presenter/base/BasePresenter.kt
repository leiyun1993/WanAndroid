package com.githubly.wanandroid.presenter.base

/**
 * 类名：BasePresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-07-13 10:40
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
abstract class BasePresenter<T : IView>(view: T) {
    protected var mView: T? = view

    fun onDestroy() {
        mView = null
    }

}