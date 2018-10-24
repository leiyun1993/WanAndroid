package com.githubly.wanandroid.utils

import android.content.Context

/**
 * 类名：AppUtils
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-24 10:13
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
object AppUtils {


    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            // 获取软件版本名称
            versionName = context.packageManager.getPackageInfo(
                    context.packageName, 0).versionName
        } catch (e: Exception) {
            return versionName
        }

        return versionName
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): Int {
        // 获取软件版本号
        var versionCode = 0L
        try {
            versionCode = context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode
        } catch (e: Exception) {
            return versionCode.toInt()
        }

        return versionCode.toInt()
    }
}