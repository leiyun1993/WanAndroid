package com.githubly.wanandroid.activity

import android.app.Activity
import android.view.View
import com.githubly.wanandroid.App
import com.githubly.wanandroid.R
import com.githubly.wanandroid.activity.base.BaseActivity
import com.githubly.wanandroid.contract.ILoginContract
import com.githubly.wanandroid.model.User
import com.githubly.wanandroid.presenter.LoginPresenter
import com.githubly.wanandroid.utils.hideSoftInput
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_login.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * LoginActivity
 */
class LoginActivity : BaseActivity<LoginPresenter>(), ILoginContract.View {


    companion object {
        fun active(act: Activity) {
            act.startActivity<LoginActivity>()
        }
    }

    private var isSelectLogin: Boolean = true

    override val inflateId: Int
        get() = R.layout.activity_login

    override fun initView() {
        btnBack.setOnClickListener {
            onBackPressed()
        }
        loginTag.setColorFilter(resources.getColor(R.color.black_1))
        registerTag.setColorFilter(resources.getColor(R.color.black_1))
        loginBtn.setOnClickListener {
            showLoginOrRegister(true)
        }
        registerBtn.setOnClickListener {
            showLoginOrRegister(false)
        }
        btnGoLogin.setOnClickListener {
            login()
        }
        btnGoRegister.setOnClickListener {
            register()
        }
    }

    override fun initData() {

    }

    override fun initPresenter(): LoginPresenter? = LoginPresenter(this)

    private fun login() {
        val userName = loginNameTV.text.toString()
        val password = loginPasswordTv.text.toString()
        if (userName.isBlank()) {
            toast("请输入用户名")
            return
        }
        if (password.isBlank()) {
            toast("请输入密码")
            return
        }
        hideSoftInput()
        showLoading()
        mPresenter?.login(userName, password)
    }

    private fun register() {
        val userName = registerNameTv.text.toString()
        val password = registerPwdTv.text.toString()
        val rePassword = registerRePwdTv.text.toString()
        if (userName.isBlank()) {
            toast("请输入用户名")
            return
        }
        if (password.isBlank()) {
            toast("请输入密码")
            return
        }
        if (rePassword.isBlank()) {
            toast("请输入密码")
            return
        }
        if (password != rePassword) {
            toast("密码不一致，请确认")
            return
        }
        hideSoftInput()
        showLoading()
        mPresenter?.register(userName, password, rePassword)
    }

    private fun showLoginOrRegister(isLogin: Boolean) {
        isSelectLogin = isLogin
        loginTag.visibility = if (isLogin) View.VISIBLE else View.INVISIBLE
        registerTag.visibility = if (!isLogin) View.VISIBLE else View.INVISIBLE
        loginLayout.visibility = if (isLogin) View.VISIBLE else View.INVISIBLE
        registerLayout.visibility = if (!isLogin) View.VISIBLE else View.INVISIBLE
    }

    override fun onLoginSuccess(user: User) {
        dismissLoading()
        App.instance.user = user
        EventBus.getDefault().post(user)
        finish()
    }

    override fun onLoginFailed(msg: String) {
        dismissLoading()
        toast(msg)
    }

    override fun onRegisterSuccess(user: User) {
        dismissLoading()
        toast("注册并成功登录")
        App.instance.user = user
        EventBus.getDefault().post(user)
        finish()
    }

    override fun onRegisterFailed(msg: String) {
        dismissLoading()
        toast(msg)
    }
}
