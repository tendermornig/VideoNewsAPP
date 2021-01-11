package com.example.videonews.logic.network

import com.example.videonews.utils.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Miracle
 * 服务创建者单例类
 */
object ServiceCreator {

    /**
     * retrofit实例
     * url为基础url
     * 转换器为gson转换器
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
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