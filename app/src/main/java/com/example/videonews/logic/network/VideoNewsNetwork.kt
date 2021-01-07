package com.example.videonews.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author Miracle
 * 统一的网路数据源访问入口
 */
object VideoNewsNetwork {

    /**
     * 用户接口动态代理对象
     */
    private val userService = ServiceCreator.create<UserService>()

    /**
     * 视频接口动态代理对象
     */
    private val videoService = ServiceCreator.create<VideoService>()

    /**
     * 资讯接口动态代理对象
     */
    private val newsService = ServiceCreator.create<NewsService>()

    suspend fun userLogin(requestParam: Map<String, String>) =
        userService.userLogin(requestParam).await()

    suspend fun userRegister(requestParam: Map<String, String>) =
        userService.userRegister(requestParam).await()

    suspend fun getVideoList(category: Int, token: String) =
        videoService.getVideoList(category, token).await()

    suspend fun getVideoCategory(token: String) =
        videoService.getVideoCategory(token).await()

    suspend fun getNewsList(requestParam: Map<String, Int>, token: String) =
        newsService.getNewsList(requestParam, token).await()

    suspend fun getNewsThumb(newsId: Int, token: String) =
        newsService.getNewsThumb(newsId, token).await()

    /**
     * 通过协程来简化数据访问回调
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)
                    else it.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}