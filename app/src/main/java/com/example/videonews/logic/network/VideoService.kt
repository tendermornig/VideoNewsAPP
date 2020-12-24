package com.example.videonews.logic.network

import com.example.videonews.logic.model.BaseResponse
import com.example.videonews.logic.model.CategoryModel
import com.example.videonews.logic.model.VideoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VideoService {

    @GET("list/category")
    fun getCategory(@Header("token") token: String): Call<BaseResponse<List<CategoryModel>>>

    @GET("list/video")
    fun getVideoList(
        @Header("token") token: String,
        @Query("category")category: Int
    ): Call<BaseResponse<List<VideoModel>>>
}