package com.githubly.wanandroid.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 类名：ApiHelper
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 16:19
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
object ApiHelper {

    val api: ApiService

    init {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)
        builder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://wanandroid.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
        val retrofit = retrofitBuilder.build()
        api = retrofit.create(ApiService::class.java)
    }
}