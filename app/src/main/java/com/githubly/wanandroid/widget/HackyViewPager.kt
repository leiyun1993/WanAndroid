package com.githubly.wanandroid.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.githubly.wanandroid.R

/**
 * 类名：HackyViewPager
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-10-24 17:38
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class HackyViewPager : ViewPager {

    var isLock = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.HackyViewPager, 0, 0)
        isLock = a.getBoolean(R.styleable.HackyViewPager_hvp_is_lock, false)
        a.recycle()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isLock) {
            return try {
                super.onInterceptTouchEvent(ev)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                false
            }

        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (!isLock) {
            super.onTouchEvent(event)
        } else false
    }

    fun toggleLock() {
        isLock = !isLock
    }

}