package com.githubly.wanandroid.net

import com.githubly.wanandroid.model.BaseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 类名：ApiCallBack
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 17:14
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ApiCallBack<T>(val result: BaseResult<T>.() -> Unit) : Callback<BaseResult<T>> {

    override fun onResponse(call: Call<BaseResult<T>>, response: Response<BaseResult<T>>) {
        val code = response.code()
        if (code in 200..299) {
            response.body()!!.result()
        } else {
            onFailure(call, RuntimeException("response error,detail = " + response.raw().toString()))
        }
    }

    override fun onFailure(call: Call<BaseResult<T>>, throwable: Throwable) {
        val error = when (throwable) {
            is SocketTimeoutException -> "网络不给力！"
            is ConnectException -> "当前的网络不通！"
            is UnknownHostException -> "当前的网络不通！"
            else -> "当前服务异常！"
        }
        BaseResult<T>().apply {
            errorCode = -1
            errorMsg = error
        }.result()
    }
}