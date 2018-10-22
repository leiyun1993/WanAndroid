package com.githubly.wanandroid.net

import com.githubly.wanandroid.utils.UserInfoHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * 保存Cookie
 */
class SaveCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            UserInfoHelper.saveCookie(cookies)
        }
        return originalResponse
    }
}
