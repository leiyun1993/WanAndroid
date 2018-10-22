package com.githubly.wanandroid.presenter

import com.githubly.wanandroid.contract.ICollectListContract
import com.githubly.wanandroid.net.ApiCallBack
import com.githubly.wanandroid.net.ApiHelper
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：CollectListPresenter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 17:48
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class CollectListPresenter(view: ICollectListContract.View) : BasePresenter<ICollectListContract.View>(view), ICollectListContract.Presenter {
    override fun getCollectList(page: Int) {
        ApiHelper.api.getCollectList(page - 1).enqueue(ApiCallBack {
            if (isSuccess) {
                data?.datas?.forEach {
                    it.collect = true
                }
                if (data?.datas?.isEmpty() == true) {
                    mView?.onListFailed("暂无收藏数据！")
                } else {
                    mView?.onListSuccess(data!!)
                }
            } else {
                mView?.onListFailed(errorMsg)
            }
        })
    }
}