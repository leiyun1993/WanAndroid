package com.githubly.wanandroid.model

/**
 * 类名：BaseListData
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 17:54
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class BaseListData<T> {
    val curPage: Int = 0
    val datas: MutableList<T> = mutableListOf()
    val offset: Int = 0
    val over: Boolean = true
    val pageCount: Int = 0
    val size: Int = 0
    val total: Int = 0
}