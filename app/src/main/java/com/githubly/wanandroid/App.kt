package com.githubly.wanandroid

import android.app.Application
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper

/**
 * 类名：App
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 10:05
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        BGASwipeBackHelper.init(this, null)
    }

}