package com.example.videonews.logic.network

import com.example.videonews.logic.model.BaseResponse
import com.example.videonews.logic.model.CategoryModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface VideoNewsListService {

    @GET("list/category")
    fun getCategory(@Header("token")token: String): Call<BaseResponse<CategoryModel>>
}