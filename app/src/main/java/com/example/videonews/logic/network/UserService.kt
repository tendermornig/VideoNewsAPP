package com.example.videonews.logic.network

import com.example.videonews.logic.model.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("user/login")
    fun userLogin(@Body loginParam: Map<String, String>): Call<BaseResponse<String>>
}