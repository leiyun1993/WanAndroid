package com.githubly.wanandroid.fragment.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubly.wanandroid.presenter.base.BasePresenter

/**
 * 类名：BaseFragment
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 19:34
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
abstract class BaseFragment<out P : BasePresenter<*>> : Fragment() {
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

    //kotlin 懒加载，在第一次使用Presenter时初始化，这种设计师针对一个View只针对一个Presenter。
    //多个Presenter的情况此处不应该使用泛型
    protected val mPresenter: P? by lazy { initPresenter() }

    abstract fun initPresenter(): P?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(inflateId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //view和presenter解绑
        mPresenter?.onDestroy()
    }
}