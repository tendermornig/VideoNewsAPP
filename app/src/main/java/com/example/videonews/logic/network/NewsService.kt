package com.example.videonews.logic.network

import com.example.videonews.logic.model.BaseResponse
import com.example.videonews.logic.model.News
import com.example.videonews.logic.model.Thumb
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * @author Miracle
 * 资讯接口类
 */
interface NewsService {

    /**
     * 获取资讯集合接口
     * @param requestParam 请求参数集合
     * 需求参数为两个
     *  page 页数
     *  limit 资讯个数
     * @param token 用户登录验证token
     * @return 资讯集合
     */
    @GET("news/list")
    fun getNewsList(
        @QueryMap requestParam: Map<String, Int>,
        @Header("token") token: String
    ): Call<BaseResponse<List<News>>>

    /**
     * 获取资讯略缩图接口
     * @param newsId 资讯id
     * @param token 用户登录验证token
     * @return 资讯略缩图集合
     */
    @GET("news/thumb")
    fun getNewsThumb(
        @Query("newsId") newsId: Int,
        @Header("token") token: String
    ): Call<BaseResponse<List<Thumb>>>
}