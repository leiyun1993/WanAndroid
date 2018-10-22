package com.githubly.wanandroid.fragment


import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.githubly.wanandroid.R
import com.githubly.wanandroid.adapter.HomeAdapter
import com.githubly.wanandroid.contract.IHomeContract
import com.githubly.wanandroid.fragment.base.BaseFragment
import com.githubly.wanandroid.model.ArticleItem
import com.githubly.wanandroid.model.Banner
import com.githubly.wanandroid.model.BaseListData
import com.githubly.wanandroid.presenter.HomePresenter
import com.githubly.wanandroid.utils.loadImage
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_banner.view.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast

/**
 * HomeFragment
 *
 */
class HomeFragment : BaseFragment<HomePresenter>(), IHomeContract.View {


    private val mAdapter by lazy { HomeAdapter() }
    private lateinit var mBannerView: BGABanner
    private var page = 1

    override val inflateId: Int
        get() = R.layout.fragment_home

    override fun initView() {
        val bannerView = View.inflate(activity, R.layout.layout_home_banner, null)
        mBannerView = bannerView.bannerView
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(activity).color(act.resources.getColor(R.color.backgroundColor)).size(
                        dip(8)
                ).visibilityProvider { position, parent ->
                    position == 0
                }.build()
        )
        mAdapter.addHeaderView(bannerView)
        mBannerView.setAdapter { banner, itemView, model, position ->
            val imageView: ImageView = itemView as ImageView
            val bannerItem = model as Banner
            imageView.loadImage(bannerItem.imagePath)
        }
        mAdapter.setOnLoadMoreListener({
            page++
            mPresenter?.getHomeArticle(page)
        }, recyclerView)
        mAdapter.setPreLoadNumber(2)
        refreshLayout.setOnRefreshListener {
            initData()
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            toast("position:$position")
        }
        mAdapter.emptyView = mLoadingStateView
        mLoadingStateView.setOnReloadClickListener {
            initData()
        }
    }

    override fun initPresenter(): HomePresenter? = HomePresenter(this)

    override fun initData() {
        mLoadingStateView.loading()
        page = 1
        mPresenter?.getBanner()
        mPresenter?.getHomeArticle(page)
    }

    override fun onBannerSuccess(banners: MutableList<Banner>) {
        val tips = mutableListOf<String>()
        banners.forEach {
            tips.add(it.title)
        }
        mBannerView.setData(banners, tips)
    }

    override fun onBannerFailed(msg: String) {

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
        mLoadingStateView.success()
    }

    override fun onArticleFailed(msg: String) {
        if (page == 1) {
            mAdapter.setNewData(mutableListOf())
            mLoadingStateView.failed(msg)
            refreshLayout.isRefreshing = false
        } else {
            toast(msg)
            mAdapter.loadMoreComplete()
        }
    }
}
