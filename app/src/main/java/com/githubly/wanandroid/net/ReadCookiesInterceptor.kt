package com.githubly.wanandroid.net

import com.githubly.wanandroid.utils.UserInfoHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 读取cookie并设置
 */
class ReadCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = UserInfoHelper.getCookie()
        preferences?.let {
            for (cookie in preferences) {
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}
