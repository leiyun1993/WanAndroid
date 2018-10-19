package com.githubly.wanandroid.model
import com.google.gson.annotations.SerializedName


/**
 * 类名：Banner
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 11:30
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

data class Banner(
    @SerializedName("desc") var desc: String = "",
    @SerializedName("id") var id: Int = 0,
    @SerializedName("imagePath") var imagePath: String = "",
    @SerializedName("isVisible") var isVisible: Int = 0,
    @SerializedName("order") var order: Int = 0,
    @SerializedName("title") var title: String = "",
    @SerializedName("type") var type: Int = 0,
    @SerializedName("url") var url: String = ""
)