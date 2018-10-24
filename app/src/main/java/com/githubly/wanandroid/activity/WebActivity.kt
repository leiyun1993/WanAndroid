package com.githubly.wanandroid.activity

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.http.SslError
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import com.githubly.wanandroid.App
import com.githubly.wanandroid.R
import com.githubly.wanandroid.activity.base.BaseActivity
import com.githubly.wanandroid.contract.IContentContract
import com.githubly.wanandroid.model.EventArticleCollectChange
import com.githubly.wanandroid.model.EventRemoveCollectPageArticle
import com.githubly.wanandroid.presenter.ContentPresenter
import com.githubly.wanandroid.widget.*
import kotlinx.android.synthetic.main.activity_web.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 类名：WebActivity
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-18 19:10
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class WebActivity : BaseActivity<ContentPresenter>(), IContentContract.View {


    companion object {
        fun active(act: Activity, link: String, id: Int, author: String, title: String, collect: Boolean, originId: Int = 0) {
            act.startActivity<WebActivity>("link" to link,
                    "id" to id,
                    "author" to author,
                    "title" to title,
                    "originId" to originId,
                    "collect" to collect)
        }
    }

    private val webView: WebView by lazy { WebView(this) }
    private var url: String = ""
    private var id: Int = -1
    private var author: String = ""
    private var title: String = ""
    private var collect: Boolean = false
    private var originId: Int = 0
    override val inflateId: Int
        get() = R.layout.activity_web

    override fun initView() {
        url = intent?.getStringExtra("link") ?: "http://www.wanandroid.com/"
        author = intent?.getStringExtra("author") ?: "wanAndroid"
        title = intent?.getStringExtra("title") ?: "wanAndroid"
        id = intent?.getIntExtra("id", -1) ?: -1
        originId = intent?.getIntExtra("originId", 0) ?: 0
        collect = intent?.getBooleanExtra("collect", false) ?: false

        btnBack.setOnClickListener { onBackPressed() }
        btnClose.setOnClickListener { finish() }
        btnOther.visibility = if (id == -1) View.GONE else View.VISIBLE
        btnOther.setOnClickListener { showOtherDialog() }
        webView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        webLayout.addView(webView)

        webView.settings.apply {
            allowFileAccess = false
            javaScriptEnabled = false
            loadWithOverviewMode = true
            useWideViewPort = true
            defaultTextEncodingName = "gb2312"
            setAppCacheEnabled(true)
            mediaPlaybackRequiresUserGesture = false
            cacheMode = WebSettings.LOAD_DEFAULT
            databaseEnabled = true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            blockNetworkImage = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        webView.webViewClient = object : WebViewClient() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }

            // 即使加载失败后，webview执行完onReceivedError()方法也会执行这个方法
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                webView.settings.blockNetworkImage = false
                if (!TextUtils.isEmpty(view.title)) {
                    titleTv.text = view.title//webView获取到网页title
                }
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                handler.proceed()
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressbar.progress = newProgress
                if (newProgress >= 100) {
                    view?.post {
                        progressbar.visibility = View.GONE
                        if (view.canGoBack()) {
                            btnClose.visibility = View.VISIBLE
                        } else {
                            btnClose.visibility = View.GONE
                        }
                    }
                } else {
                    if (progressbar.visibility == View.GONE) {
                        view?.post { progressbar.visibility = View.VISIBLE }
                    }
                }
            }
        }
        WebView.setWebContentsDebuggingEnabled(true)        //将 WebViews 配置为可调试状态
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }

    override fun initData() {
        webView.loadUrl(url)
    }

    override fun initPresenter(): ContentPresenter? = ContentPresenter(this)

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.apply {
            webViewClient = null
            webChromeClient = null
            (parent as ViewGroup).removeView(webView)
            removeAllViews()
            destroy()
        }
    }

    private fun showOtherDialog() {
        val dialog = OtherBottomDialog.getDialog(collect)
        dialog.show(supportFragmentManager, "otherDialog")
        dialog.onItemClick = {
            when (it) {
                TYPE_SHARE -> {
                    share(url, title)
                }
                TYPE_COLLECT -> {
                    if (App.instance.isLogin) {
                        if (!collect) {
                            mPresenter?.articleCollect(id)
                        } else {
                            if (originId == 0) {
                                mPresenter?.articleUncollect(id)
                            } else {
                                mPresenter?.articleUncollectPage(id, originId)
                            }
                        }
                    } else {
                        LoginActivity.active(this)
                    }
                }
                TYPE_COPY -> {
                    copyUrl()
                }
                TYPE_BROWSER -> {
                    browse(url)
                }
            }
            dialog.dismiss()
        }
    }

    override fun onCollectSuccess() {
        toast("收藏成功")
        collect = true
        EventBus.getDefault().post(EventArticleCollectChange(id, collect))
    }

    override fun onCollectFailed(msg: String) {
        toast(msg)
    }

    override fun onUncollectSuccess() {
        toast("取消收藏成功")
        collect = false
        EventBus.getDefault().post(EventArticleCollectChange(id, collect))
    }

    override fun onUncollectFailed(msg: String) {
        toast(msg)
    }

    override fun onUncollectPageSuccess() {
        toast("取消收藏成功")
        collect = false
        btnOther.visibility = View.GONE
        EventBus.getDefault().post(EventRemoveCollectPageArticle(id))
    }

    override fun onUncollectPageFailed(msg: String) {
        toast(msg)
    }

    /**
     * 复制链接
     */
    private fun copyUrl() {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.primaryClip = ClipData.newPlainText(null, url)
        toast("已成功复制!")
    }
}