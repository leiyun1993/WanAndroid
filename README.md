# 玩Android

关注[wanandroid](http://www.wanandroid.com)有些时日了，一直以来也是鸿洋大神的粉丝，一直都有用他开放的[api](http://www.wanandroid.com/blog/show/2)写一个App的打算，最近时间比较充裕，所以就抽时间完成了这样一款。Github上的wanadnroidApp已经非常的多了。有很多也很优秀，但是我觉得开放api的目的是让大家都参与其中，所以这款app是我完全按照自己的思维去写的，UI想怎么写就怎么写，架构想怎么写就怎么写，又不是上班时间，别对自己有那么多的要求。只求更熟练的掌握Kotlin和打发多余的时间，当然这个项目能给予大家消遣或者给新手一些启发，那就是赚到了！

现在也不是完整版本，后续有时间会持续的更新此App（也就是想到想优化的东西的时候，或者鸿洋大神更新了他的api的时候），希望大家看的开心、玩的愉快~

## 简介

本项目使用 kotlin+mvp+retrofit+anko+eventbus+glide，用的都是一些很主流的框架。当然项目中其实没有太多的技术难点，主要是当你有一项技能之后，不做点自己的东西，手就会痒~~
* [kotlin](https://github.com/JetBrains/kotlin)
* [anko](https://github.com/Kotlin/anko)
* [retrofit2](https://github.com/square/retrofit)
* [okhttp3](https://github.com/square/okhttp)
* [BGABanner](https://github.com/bingoogolapple/BGABanner-Android)
* [BGASwipeBack](https://github.com/bingoogolapple/BGASwipeBackLayout-Android)
* [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* [glide](https://github.com/bumptech/glide)
* [eventbus](https://github.com/greenrobot/EventBus)
* [PermissionsDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher)(更新功能需要文件读写权限)
* retrofit2:converter-gson(GsonConverterFactory)
* okhttp3:logging-interceptor(HttpLoggingInterceptor)
* bugly(更新、统计、异常上报)
* font（静蕾体）
 
## 项目截图

![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/11.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/1.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/2.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/3.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/4.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/5.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/6.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/7.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/8.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/9.png)
![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/10.png)

# 部分功能解析

### 1、MVP
极简的MVP设计，这是此前自己参考一些项目之后写的[MVPSample](https://github.com/leiyun1993/AndroidNotes),适合这种小项目玩耍
```kotlin
abstract class BaseActivity<out P : BasePresenter<*>> : AppCompatActivity(){
    protected val mPresenter: P? by lazy { initPresenter() }
}
```
```kotlin
abstract class BasePresenter<T : IView>(view: T) {
    protected var mView: T? = view
    
    fun onDestroy() {
        mView = null
    }
}
```
```kotlin
interface IArticlePageContract {

    interface Presenter{
        fun getxxxx()
    }

    interface View:IView{
        fun onxxxxSuccess(data: xxxx)
        fun onxxxxFailed(msg: String)
    }
}
```
### 2、网络请求
普通的**Retrofit**封装，使用**GsonConverterFactory**解析数据，保持登录使用的是[ReadCookiesInterceptor](https://github.com/leiyun1993/WanAndroid/blob/master/app/src/main/java/com/githubly/wanandroid/net/ReadCookiesInterceptor.kt)和[SaveCookiesInterceptor](https://github.com/leiyun1993/WanAndroid/blob/master/app/src/main/java/com/githubly/wanandroid/net/SaveCookiesInterceptor.kt)读取和保存Cookie。
ApiCallBack使用lambda回调，并进一步简化回调信息,合并为BaseResult同意处理。
```kotlin
class ApiCallBack<T>(val result: BaseResult<T>.() -> Unit) : Callback<BaseResult<T>> {

    override fun onResponse(call: Call<BaseResult<T>>, response: Response<BaseResult<T>>) {
        val code = response.code()
        if (code in 200..299) {
            val errorCode = response.body()?.errorCode
            if (errorCode == -1001) {    //需要重新登录
                App.instance.user = null
            } else {
                response.body()!!.result()
            }
        } else {
            onFailure(call, RuntimeException("response error,detail = " + response.raw().toString()))
        }
    }

    override fun onFailure(call: Call<BaseResult<T>>, throwable: Throwable) {
        val error = when (throwable) {
            is SocketTimeoutException -> "网络不给力！"
            is ConnectException -> "当前的网络不通！"
            is UnknownHostException -> "当前的网络不通！"
            else -> "当前服务异常！"
            //可进一步细分错误类型
        }
        BaseResult<T>().apply {
            errorCode = -1
            errorMsg = error
        }.result()
    }
}
```
这样处理回调的时候就可以很简单的如下处理：
```kotlin
ApiHelper.api.xxxx(xxxx).enqueue(ApiCallBack {
    if (isSuccess) {
        mView?.onXxxxSuccess(data!!)
    } else {
        mView?.onXxxxFailed(errorMsg)
    }
})
```
### 3、推荐一波[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
这个包含了自动loadmore，emptyView，header，footer，moreType等一些列很实用的功能，使用后RecyclerView的适配器如下，非常方便的使用：
```kotlin
class HomeAdapter : BaseQuickAdapter<ArticleItem, BaseViewHolder>(R.layout.item_home_article) {

    override fun convert(helper: BaseViewHolder?, item: ArticleItem?) {
        val itemView = helper?.itemView
        itemView?.apply {
            item?.let {
                //do something
            }
        }
    }
}
```
### 4、关于页面
这是个加载的本地Html，是抠了[WanAndroid-About](http://www.wanandroid.com/about)然后加上了关于本App的介绍，使用了自己仅有CSS技术做了一个简单的手机适配。（最近大家也知道，感觉Android越来越不景气了，大家还从事android的一定要拓展，要嘛更深，要嘛更广~~~说实话好想转行）

![image](https://github.com/leiyun1993/WanAndroid/raw/master/screenshot/12.jpg)

# 版本信息
### v1.1.0.0(TODO)
- [ ] 知识体系
- [ ] TODO工具
- [ ] 搜索功能
- [ ] 优化项目页瀑布流显示
### v1.0.0.3
- [x] 引入bugly，增加升级功能
### v1.0.0.2
- [x] 删除部分无用的库
### v1.0.0.1
- [x] 优化title显示
### v1.0.0.0
- [x] 首页文章与Banner
- [x] 微信文章
- [x] 项目
- [x] 登录注册
- [x] 我的收藏

# TKS
* 感谢鸿洋大神创建了这么好的学习网站[WanAndroid](http://www.wanandroid.com)
* 感谢大神百忙之中抽空写的[开放api](https://github.com/hongyangAndroid/wanandroid)
* 感谢项目中使用到的各种库的无私奉献

# License
```text
Copyright 2018 YunLei

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```