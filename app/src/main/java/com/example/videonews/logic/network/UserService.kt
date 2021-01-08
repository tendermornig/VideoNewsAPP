package com.example.videonews.logic.network

import com.example.videonews.base.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author Miracle
 * 用户接口类
 */
interface UserService {

    /**
     * 用户登录接口
     * @param requestParam 用户登录参数集合
     * 需求参数为两个
     *  userAccount 用户登录账号
     *  userPassword 用户登录密码
     *  @return 登录结果
     */
    @POST("user/login")
    fun userLogin(@Body requestParam: Map<String, String>): Call<BaseResponse<String>>

    /**
     * 用户注册接口
     * @param requestParam 用户注册参数集合
     * 需求参数为两个
     *  userAccount 用户注册账号
     *  userPassword 用户注册密码
     *  @return 注册结果
     */
    @POST("user/register")
    fun userRegister(@Body requestParam: Map<String, String>): Call<BaseResponse<String>>
}