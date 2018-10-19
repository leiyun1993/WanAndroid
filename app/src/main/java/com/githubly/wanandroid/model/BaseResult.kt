package com.githubly.wanandroid.model

/**
 * 类名：BaseResult
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 17:16
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class BaseResult<T> {
    var data: T? = null
    var errorCode: Int = -1
    var errorMsg: String = ""

    val isSuccess: Boolean
        get() = errorCode == 0
}