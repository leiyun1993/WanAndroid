package com.githubly.wanandroid

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.githubly.wanandroid.activity.base.BaseActivity
import com.githubly.wanandroid.adapter.HomeViewPagerAdapter
import com.githubly.wanandroid.fragment.ArticleFragment
import com.githubly.wanandroid.fragment.HomeFragment
import com.githubly.wanandroid.fragment.ProjectFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity
 */
class MainActivity : BaseActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private val fragments = mutableListOf<Fragment>()
    private var bottomViews = mutableListOf<View>()


    override val inflateId: Int
        get() = R.layout.activity_main

    override fun initView() {
        fragments.add(HomeFragment())
        fragments.add(ArticleFragment())
        fragments.add(ProjectFragment())
        fragments.add(HomeFragment())

        homeViewPager.adapter = HomeViewPagerAdapter(fragments, supportFragmentManager)
        homeViewPager.offscreenPageLimit = 4
        homeViewPager.addOnPageChangeListener(this)
        bottomViews.add(rl_home)
        bottomViews.add(rl_article)
        bottomViews.add(rl_category)
        bottomViews.add(rl_my)

        for (view in bottomViews) {
            view.setOnClickListener(this)
        }
        setBottomBarSelect(0)
    }

    override fun initData() {

    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        for (bottomView in bottomViews) {
            if (v == bottomView) {
                homeViewPager.setCurrentItem(bottomViews.indexOf(bottomView), false)
                setBottomBarSelect(bottomViews.indexOf(bottomView))
            }
        }
    }

    private fun setBottomBarSelect(index: Int) {
        for (view in bottomViews) {
            view.isSelected = false
        }
        bottomViews[index].isSelected = true
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(p0: Int) {
        setBottomBarSelect(p0)
    }

}
