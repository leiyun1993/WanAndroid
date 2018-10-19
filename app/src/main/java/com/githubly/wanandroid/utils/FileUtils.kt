package com.githubly.wanandroid.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * 类名：FileUtils
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 16:29
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
object FileUtils {

    /**
     * 从assets读取文本内容
     *
     * @param context
     * @param fileName
     * @return
     */
    fun getFromAssets(context: Context, fileName: String): String {
        val result = StringBuffer()
        var inputReader: InputStreamReader? = null
        var bufReader: BufferedReader? = null
        try {
            inputReader = InputStreamReader(context.resources.assets.open(fileName), "UTF-8")
            bufReader = BufferedReader(inputReader)
            bufReader.readLines().forEach {
                result.append(it)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputReader?.close()
                bufReader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return result.toString().trim()
    }
}