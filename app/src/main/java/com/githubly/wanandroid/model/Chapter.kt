package com.githubly.wanandroid.model
import com.google.gson.annotations.SerializedName


/**
 * 类名：Chapter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 16:33
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

data class Chapter(
    @SerializedName("children") var children: List<Any> = listOf(),
    @SerializedName("courseId") var courseId: Int = 0,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("order") var order: Int = 0,
    @SerializedName("parentChapterId") var parentChapterId: Int = 0,
    @SerializedName("userControlSetTop") var userControlSetTop: Boolean = false,
    @SerializedName("visible") var visible: Int = 0
)