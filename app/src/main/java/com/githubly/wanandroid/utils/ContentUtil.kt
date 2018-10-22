package com.githubly.wanandroid.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.support.v4.act


/**
 * 类名：ContentUtil
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 19:45
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

fun Activity.hideSoftInput(view: View = window.peekDecorView()) {
    val inputManger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManger.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showSoftInput(view: View = window.peekDecorView()) {
    val inputManger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManger.showSoftInput(view, 0)
}

fun Fragment.hideSoftInput(view: View? = getView()) {
    if (view != null) {
        val inputManger = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManger.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.showSoftInput(view: View? = getView()) {
    val inputManger = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManger.showSoftInput(view, 0)
}