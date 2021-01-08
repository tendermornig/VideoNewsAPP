package com.example.videonews.base

/**
 * @author Miracle
 * 基础初始化接口 提供初始方法给实现类重写
 */
interface BaseInit<T> {

    /**
     * 初始化ViewBinding
     * @return 初始化好的ViewBinding
     */
    fun initViewBinding() : T

    /**
     * 初始化视图
     */
    fun initView()

    /**
     * 初始化数据
     */
    fun initData()
}