package com.githubly.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.githubly.wanandroid.R
import com.githubly.wanandroid.adapter.WXArticleAdapter
import com.githubly.wanandroid.contract.IProjectPageContract
import com.githubly.wanandroid.fragment.base.BaseFragment
import com.githubly.wanandroid.model.ArticleItem
import com.githubly.wanandroid.model.BaseListData
import com.githubly.wanandroid.presenter.ProjectPagePresenter
import kotlinx.android.synthetic.main.fragment_project_page.*
import org.jetbrains.anko.support.v4.withArguments

/**
 * 类名：ProjectPageFragment
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-19 16:42
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ProjectPageFragment : BaseFragment<ProjectPagePresenter>(), IProjectPageContract.View {


    companion object {
        fun getNewInstance(userId: Int): ProjectPageFragment {
            val frag = ProjectPageFragment()
            frag.withArguments("userId" to userId)
            return frag
        }
    }

    private val mAdapter by lazy { WXArticleAdapter() }
    private var page = 1
    private var userId = 99999
    override val inflateId: Int
        get() = R.layout.fragment_project_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt("userId") ?: 99999
    }

    override fun initView() {
        mAdapter.userID = userId
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        /*recyclerView.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(activity).color(act.resources.getColor(R.color.backgroundColor)).size(
                        dip(8)
                ).build()
        )*/
        mAdapter.setOnLoadMoreListener({
            page++
            mPresenter?.getProjectArticle(userId, page)
        }, recyclerView)
        mAdapter.setPreLoadNumber(2)
        refreshLayout.setOnRefreshListener {
            initData()
        }
    }

    override fun initPresenter(): ProjectPagePresenter? = ProjectPagePresenter(this)

    override fun initData() {
        page = 1
        mPresenter?.getProjectArticle(userId, page)
    }


    override fun onArticleSuccess(data: BaseListData<ArticleItem>) {
        if (data.curPage == 1) {
            mAdapter.setNewData(data.datas)
            refreshLayout.isRefreshing = false
        } else {
            mAdapter.addData(data.datas)
            mAdapter.loadMoreComplete()
        }
        page = data.curPage
        if (data.over) {
            mAdapter.loadMoreEnd()
        }
        mAdapter.setEnableLoadMore(!data.over)
    }

    override fun onArticleFailed(msg: String) {
    }
}