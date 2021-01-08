package com.example.videonews.logic.network

import com.example.videonews.base.BaseResponse
import com.example.videonews.logic.model.Category
import com.example.videonews.logic.model.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @author Miracle
 * 视频接口类
 */
interface VideoService {

    /**
     * 获取视频集合接口
     * @param category 视频类别
     * @param token 用户登录验证token
     * @return 视频集合
     */
    @GET("video/list")
    fun getVideoList(
        @Query("category")category: Int,
        @Header("token") token: String
    ): Call<BaseResponse<List<Video>>>

    /**
     * 获取视频分类接口
     * @param token 用户登录验证token
     * @return 视频分类
     */
    @GET("video/category")
    fun getVideoCategory(@Header("token") token: String): Call<BaseResponse<List<Category>>>
}