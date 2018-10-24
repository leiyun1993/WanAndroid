package com.githubly.wanandroid.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet

/**
 * 类名：ScrollTextView
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 19:10
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ScrollTextView : android.support.v7.widget.AppCompatTextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        ellipsize = TextUtils.TruncateAt.MARQUEE
        marqueeRepeatLimit = -1
        setSingleLine(true)
    }

    override fun isFocused(): Boolean {
        return true
    }
}
