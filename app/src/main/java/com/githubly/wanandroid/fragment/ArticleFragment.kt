package com.githubly.wanandroid.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.githubly.wanandroid.R
import com.githubly.wanandroid.fragment.base.BaseFragment
import com.githubly.wanandroid.model.Chapter
import com.githubly.wanandroid.presenter.base.BasePresenter
import com.githubly.wanandroid.utils.FileUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_article.*
import org.jetbrains.anko.support.v4.act

/**
 * 类名：ArticleFragment
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 16:12
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ArticleFragment : BaseFragment<BasePresenter<*>>() {

    private lateinit var chaptList: MutableList<Chapter>

    override val inflateId: Int
        get() = R.layout.fragment_article

    override fun initView() {

    }

    override fun initData() {
        val readFileFromAssets = FileUtils.getFromAssets(act, "chapters")
        chaptList = Gson().fromJson(readFileFromAssets, object : TypeToken<List<Chapter>>() {}.type)
        viewPager.adapter = ArticlePagerAdapter(chaptList, childFragmentManager)
        tabLayout.setViewPager(viewPager)
    }

    override fun initPresenter(): BasePresenter<*>? = null
}

class ArticlePagerAdapter(private val chaptList: MutableList<Chapter>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = ArticlePageFragment.getNewInstance(chaptList[position].id)

    override fun getCount(): Int = chaptList.size

    override fun getPageTitle(position: Int): CharSequence? = chaptList[position].name
}