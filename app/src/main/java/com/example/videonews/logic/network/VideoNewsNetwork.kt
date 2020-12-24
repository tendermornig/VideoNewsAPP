package com.example.videonews.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object VideoNewsNetwork {

    private val userService = ServiceCreator.create<UserService>()

    private val videoService = ServiceCreator.create<VideoService>()

    suspend fun userLogin(loginParam: Map<String, String>) =
        userService.userLogin(loginParam).await()

    suspend fun userRegister(registerParam: Map<String, String>) =
        userService.userRegister(registerParam).await()

    suspend fun getCategory(token: String) =
        videoService.getCategory(token).await()

    suspend fun getVideoList(token: String, category: Int) =
        videoService.getVideoList(token, category).await()

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