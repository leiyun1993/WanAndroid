package com.githubly.wanandroid.net

import com.githubly.wanandroid.model.*
import retrofit2.Call
import retrofit2.http.*

/**
 * 类名：ApiService
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 16:59
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface ApiService {

    //获取最新项目
    @GET("/article/listproject/{page}/json")
    fun getLatestProjects(@Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>


    //获取项目
    @GET("/project/list/{page}/json")
    fun getProjects(@Path("page") page: Int, @Query("cid") cid: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    //首页banner
    @GET("/banner/json")
    fun getBanner(): Call<BaseResult<MutableList<Banner>>>

    //首页文章列表
    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    //查看某个公众号历史数据
    @GET("/wxarticle/list/{userID}/{page}/json")
    fun getWXArticle(@Path("userID") userID: Int, @Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<BaseResult<User>>

    //注册
    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword: String): Call<BaseResult<User>>

    //退出登录
    @GET("/user/logout/json")
    fun logout(): Call<BaseResult<String>>

    //收藏文章列表
    @GET("/lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    //收藏文章
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Call<BaseResult<String>>

    //取消收藏文章
    @POST("/lg/uncollect_originId/{id}/json")
    fun uncollect(@Path("id") id: Int): Call<BaseResult<String>>

    //取消收藏文章--收藏页面
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    fun unCollectPage(@Path("id") id: Int, @Field("originId") originId: Int): Call<BaseResult<String>>
}