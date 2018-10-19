package com.githubly.wanandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * 类名：HomeViewPagerAdapter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-10-17 15:05
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class HomeViewPagerAdapter(private val fragments: MutableList<Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}