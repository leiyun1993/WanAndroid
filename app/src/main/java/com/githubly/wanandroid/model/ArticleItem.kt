package com.githubly.wanandroid.model

import com.google.gson.annotations.SerializedName


/**
 * 类名：ArticleItem
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 17:57
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

data class ArticleItem(
    @SerializedName("apkLink") var apkLink: String = "",
    @SerializedName("author") var author: String = "",
    @SerializedName("chapterId") var chapterId: Int = 0,
    @SerializedName("chapterName") var chapterName: String = "",
    @SerializedName("collect") var collect: Boolean = false,
    @SerializedName("courseId") var courseId: Int = 0,
    @SerializedName("desc") var desc: String = "",
    @SerializedName("envelopePic") var envelopePic: String = "",
    @SerializedName("fresh") var fresh: Boolean = false,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("link") var link: String = "",
    @SerializedName("niceDate") var niceDate: String = "",
    @SerializedName("origin") var origin: String = "",
    @SerializedName("projectLink") var projectLink: String = "",
    @SerializedName("publishTime") var publishTime: Long = 0,
    @SerializedName("superChapterId") var superChapterId: Int = 0,
    @SerializedName("superChapterName") var superChapterName: String = "",
    @SerializedName("tags") var tags: MutableList<WChatArticleTag> = mutableListOf(),
    @SerializedName("title") var title: String = "",
    @SerializedName("type") var type: Int = 0,
    @SerializedName("userId") var userId: Int = 0,
    @SerializedName("visible") var visible: Int = 0,
    @SerializedName("zan") var zan: Int = 0,
    @SerializedName("originId") var originId: Int = 0
)

data class WChatArticleTag(
    @SerializedName("name") var name: String = "",
    @SerializedName("url") var url: String = ""
)

data class EventRemoveCollectPageArticle(var id: Int)

data class EventArticleCollectChange(var id: Int,var collect: Boolean)