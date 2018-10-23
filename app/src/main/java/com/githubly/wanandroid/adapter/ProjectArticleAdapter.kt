package com.githubly.wanandroid.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.githubly.wanandroid.R
import com.githubly.wanandroid.model.ArticleItem
import com.githubly.wanandroid.utils.loadImage
import kotlinx.android.synthetic.main.item_project.view.*

/**
 * 类名：ProjectArticleAdapter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 11:11
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ProjectArticleAdapter : BaseQuickAdapter<ArticleItem, BaseViewHolder>(R.layout.item_project) {

    private val colorRess = mutableListOf(
            R.drawable.shape_head_red,
            R.drawable.shape_head_blue,
            R.drawable.shape_head_orange,
            R.drawable.shape_head_green)

    override fun convert(helper: BaseViewHolder?, item: ArticleItem?) {
        val itemView = helper?.itemView
        itemView?.apply {
            item?.let {
                nameHeadTV.text = getStrFirstChat(it.author)
                nameHeadTV.setBackgroundResource(getRandomColor(it.chapterId))
                nameTv.text = it.author
                titleTV.text = it.title
                timeTv.text = it.niceDate
                envelopePicIV.loadImage(it.envelopePic)
                collectTagTv.visibility = if (it.collect) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

    private fun getRandomColor(userID: Int): Int {
        return colorRess[userID % 4]
    }

    private fun getStrFirstChat(str: String): String {
        return str.substring(0, 1)
    }
}