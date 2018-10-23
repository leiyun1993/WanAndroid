package com.githubly.wanandroid.fragment

import android.os.Bundle
import android.view.View
import com.githubly.wanandroid.App
import com.githubly.wanandroid.R
import com.githubly.wanandroid.activity.CollectListActivity
import com.githubly.wanandroid.activity.LoginActivity
import com.githubly.wanandroid.activity.WebActivity
import com.githubly.wanandroid.contract.ILogoutContract
import com.githubly.wanandroid.fragment.base.BaseFragment
import com.githubly.wanandroid.model.User
import com.githubly.wanandroid.presenter.LogoutPresenter
import com.githubly.wanandroid.widget.OtherBottomDialog
import kotlinx.android.synthetic.main.fragment_my.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast

/**
 * 类名：MyFragment
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-22 11:01
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class MyFragment : BaseFragment<LogoutPresenter>(), ILogoutContract.View {

    override val inflateId: Int
        get() = R.layout.fragment_my

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun initView() {
        btnLogin.setOnClickListener {
            LoginActivity.active(act)
        }
        btnLogout.setOnClickListener {
            showLoading()
            mPresenter?.logout()
        }
        btnCollectList.setOnClickListener {
            CollectListActivity.active(activity!!)
        }
        btnAbout.setOnClickListener {
            val link = "file:///android_asset/web/about.html"
            WebActivity.active(act, link,-1,"wanAndroid","",false)
        }
        btnTodo.setOnClickListener {
            toast("待完成...")
        }
        btnKnowSystem.setOnClickListener {
            toast("待完成...")
        }
    }

    override fun initData() {
        setUserInfo()
    }

    override fun initPresenter(): LogoutPresenter? = LogoutPresenter(this)

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventLoginSuccess(userEvent: User) {
        setUserInfo()
    }

    private fun setUserInfo() {
        val user = App.instance.user
        user?.let {
            nameImgTv.text = it.username.substring(0, 1)
            nameTv.text = it.username
            btnLogin.visibility = View.GONE
            nameTv.visibility = View.VISIBLE
            btnLogout.visibility = View.VISIBLE
            logoutLine.visibility = View.VISIBLE
        } ?: setLogout()
    }

    private fun setLogout() {
        nameImgTv.text = ""
        nameTv.text = ""
        btnLogin.visibility = View.VISIBLE
        nameTv.visibility = View.GONE
        btnLogout.visibility = View.GONE
        logoutLine.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onLogoutSuccess() {
        dismissLoading()
        App.instance.user = null
        setLogout()
    }

    override fun onLogoutFailed(msg: String) {
        dismissLoading()
        toast(msg)
    }

}