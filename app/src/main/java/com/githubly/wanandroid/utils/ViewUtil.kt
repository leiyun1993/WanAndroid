package com.githubly.wanandroid.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubly.wanandroid.R

/**
 * 类名：ImageViewUtil
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 11:44
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
fun ImageView?.loadImage(
    url: String,
    placeHolderRes: Int = R.mipmap.ic_empty_banner,
    errorRes: Int = R.mipmap.ic_empty_banner,
    fallbackRes: Int = R.mipmap.ic_empty_banner
) {
    if (this == null) return
    Glide.with(this)
        .load(url)
        .thumbnail(0.1f)
        .apply(RequestOptions().apply {
            placeholder(placeHolderRes)
            error(errorRes)
            fallback(fallbackRes)
        })
        .into(this)
}