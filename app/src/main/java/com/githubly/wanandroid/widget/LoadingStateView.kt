package com.githubly.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.githubly.wanandroid.R
import kotlinx.android.synthetic.main.layout_page_state.view.*

/**
 * 类名：LoadingStateView
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 19:07
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class LoadingStateView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mView: View = View.inflate(context, R.layout.layout_page_state, this)


    fun loading() {
        visibility = View.VISIBLE
        mView.loadingView.visibility = View.VISIBLE
        mView.errorView.visibility = View.GONE
    }

    fun success() {
        visibility = View.GONE
    }

    fun failed(msg: String = "加载失败") {
        visibility = View.VISIBLE
        mView.loadingView.visibility = View.GONE
        mView.errorView.visibility = View.VISIBLE
        mView.errorMsgTv.text = msg
    }

    fun setOnReloadClickListener(onClick:View.() -> Unit) {
        errorView.setOnClickListener{
            it.onClick()
        }
    }
}