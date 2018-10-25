package com.githubly.wanandroid.activity.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.githubly.wanandroid.R
import com.githubly.wanandroid.presenter.base.BasePresenter
import com.githubly.wanandroid.utils.ILoading
import com.githubly.wanandroid.widget.LoadingStateView
import com.gyf.barlibrary.ImmersionBar
import com.yanzhenjie.loading.dialog.LoadingDialog

/**
 * 类名：BaseActivity
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 19:11
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
abstract class BaseActivity<out P : BasePresenter<*>> : AppCompatActivity(), BGASwipeBackHelper.Delegate, ILoading {

    protected var mBaseImmersionBar: ImmersionBar? = null
    protected var mSwipeBackHelper: BGASwipeBackHelper? = null
    /**
     * 当前页面需要加载的layoutId，等价setContentView
     */
    abstract val inflateId: Int

    /**
     * 初始化视图操作在这里执行，执行时机为onCreate之后
     */
    abstract fun initView(): Unit

    /**
     * 数据初始化在这里执行，执行时机为initView之后
     */
    abstract fun initData(): Unit

    //kotlin 懒加载，在第一次使用Presenter时初始化，这种设计是针对一个View只针对一个Presenter。
    //多个Presenter的情况此处不应该使用泛型
    protected val mPresenter: P? by lazy { initPresenter() }

    abstract fun initPresenter(): P?

    private val mWaitDialog by lazy { LoadingDialog(this) }

    protected val mLoadingStateView by lazy { LoadingStateView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        initSwipeBackFinish()
        super.onCreate(savedInstanceState)
        setContentView(inflateId)
        initView()
        initData()
        setStatusBar()
    }

    open fun setStatusBar() {
        mBaseImmersionBar = ImmersionBar.with(this)
        mBaseImmersionBar?.apply {
            statusBarColor(R.color.white)
            fitsSystemWindows(true)
            statusBarDarkFont(true, 0.2f)
            keyboardEnable(true)
            init()
        }
    }

    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)
    }

    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper?.swipeBackward()
    }

    override fun onSwipeBackLayoutSlide(slideOffset: Float) {
    }

    override fun onSwipeBackLayoutCancel() {
    }

    override fun isSupportSwipeBack(): Boolean = true

    override fun onBackPressed() {
        if (mSwipeBackHelper?.isSliding == true) {
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBaseImmersionBar?.destroy()
        dismissLoading()
    }

    override fun showLoading(msg: String) {
        mWaitDialog.setMessage(msg)
        mWaitDialog.setCancelable(false)
        mWaitDialog.setCanceledOnTouchOutside(false)
        if (!mWaitDialog.isShowing) {
            mWaitDialog.show()
        }
    }

    override fun dismissLoading() {
        if (mWaitDialog.isShowing) {
            mWaitDialog.dismiss()
        }
    }
}