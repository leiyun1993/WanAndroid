package com.githubly.wanandroid.activity

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import com.githubly.wanandroid.R
import com.githubly.wanandroid.activity.base.BaseActivity
import com.githubly.wanandroid.adapter.HomeAdapter
import com.githubly.wanandroid.contract.ICollectListContract
import com.githubly.wanandroid.model.ArticleItem
import com.githubly.wanandroid.model.BaseListData
import com.githubly.wanandroid.presenter.CollectListPresenter
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.activity_collect_list.*
import org.jetbrains.anko.act
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivity

/**
 * CollectListActivity
 */
class CollectListActivity : BaseActivity<CollectListPresenter>(), ICollectListContract.View {

    companion object {
        fun active(act: Activity) {
            act.startActivity<CollectListActivity>()
        }
    }

    private var page = 1
    private val mAdapter by lazy { HomeAdapter() }
    override val inflateId: Int
        get() = R.layout.activity_collect_list

    override fun initView() {
        btnBack.setOnClickListener { onBackPressed() }
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(this).color(act.resources.getColor(R.color.backgroundColor)).size(
                        dip(8)
                ).build()
        )
        mAdapter.setOnLoadMoreListener({
            page++
            mPresenter?.getCollectList(page)
        }, recyclerView)
        mAdapter.setPreLoadNumber(2)
        refreshLayout.setOnRefreshListener {
            initData()
        }
        mAdapter.emptyView = mLoadingStateView
    }

    override fun initData() {
        mLoadingStateView.loading()
        page = 1
        mPresenter?.getCollectList(page)
    }

    override fun initPresenter(): CollectListPresenter? = CollectListPresenter(this)

    override fun onListSuccess(data: BaseListData<ArticleItem>) {
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
        mLoadingStateView.success()
    }

    override fun onListFailed(msg: String) {
        mLoadingStateView.failed(msg)
        refreshLayout.isRefreshing = false
    }
}
