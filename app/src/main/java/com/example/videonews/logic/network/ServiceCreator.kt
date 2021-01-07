package com.example.videonews.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Miracle
 * 服务创建者单例类
 */
object ServiceCreator {

    /**
     * 基础url
     */
    private const val BASE_URL = "http://10.0.2.2:8080/VideoNews_war/api/"

    /**
     * retrofit实例
     * url为基础url
     * 转换器为gson转换器
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 创建接口动态代理对象方法
     * @param T 接口泛型
     * @param serviceClass 接口类对象
     * @return 接口动态代理对象
     */
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * 利用kotlin泛型实化功能简化动态代理对象创建
     */
    inline fun <reified T> create(): T = create(T::class.java)
}