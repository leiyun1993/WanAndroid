package com.githubly.wanandroid.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AlertDialog
import com.githubly.wanandroid.MainActivity
import com.githubly.wanandroid.R
import com.githubly.wanandroid.activity.base.BaseActivity
import com.githubly.wanandroid.presenter.base.BasePresenter
import com.githubly.wanandroid.utils.AppUtils
import com.githubly.wanandroid.utils.toAppDetail
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

/**
 * SplashActivity
 */
@RuntimePermissions
class SplashActivity : BaseActivity<BasePresenter<*>>() {

    override val inflateId: Int
        get() = R.layout.activity_splash

    override fun setStatusBar() {
        mBaseImmersionBar = ImmersionBar.with(this)
        mBaseImmersionBar?.apply {
            statusBarColor(R.color.transparent)
            statusBarDarkFont(true, 0.2f)
            init()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        versionTv.text = "v${AppUtils.getVersionName(this)}"
    }


    override fun initData() {
        goMainWithPermissionCheck()
    }

    @NeedsPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun goMain() {
        versionTv.postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 1000)
    }

    override fun initPresenter(): BasePresenter<*>? = null

    @OnPermissionDenied(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun permissionDenied() {
        showPermissionDeniedDialog()
    }

    @OnNeverAskAgain(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun neverAskAgain() {
        showPermissionDeniedDialog()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("温馨提示")
            setMessage("你拒绝了权限请求，会影响应用更新等功能的正常使用！")
            setPositiveButton("去设置") { _, _ ->
                toAppDetail(0x99)
                dismissLoading()
            }
            setNegativeButton("取消") { _, _ ->
                goMain()
                dismissLoading()
            }
            setCancelable(false)
            setFinishOnTouchOutside(false)
        }.create().show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 99) {
            goMain()
        }
    }
}
