package com.githubly.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.githubly.wanandroid.R
import com.githubly.wanandroid.model.ArticleItem
import kotlinx.android.synthetic.main.item_home_article.view.*
import java.util.*

/**
 * 类名：HomeAdapter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 11:11
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class HomeAdapter : BaseQuickAdapter<ArticleItem, BaseViewHolder>(R.layout.item_home_article) {

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
                nameHeadTV.setBackgroundResource(getRandomColor())
                nameTv.text = it.author
                titleTv.text = it.title
                descTv.text = if (it.desc.isNullOrBlank()) {
                    it.link
                } else {
                    it.desc
                }
                timeTv.text = it.niceDate
                typeTv.text = getType(it)
                heartIV.setColorFilter(context.resources.getColor(if (it.collect) {
                    R.color.red
                } else {
                    R.color.gray_1
                }))
            }
        }
    }

    private fun getType(item: ArticleItem): String {
        return if (item.superChapterName.isNullOrBlank()) {
            item.chapterName
        } else {
            "${item.chapterName} / ${item.superChapterName}"
        }
    }

    private fun getRandomColor(): Int {
        val index = Random().nextInt(4)
        return colorRess[index]
    }

    private fun getStrFirstChat(str: String): String {
        return str.substring(0, 1)
    }
}