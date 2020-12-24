package com.example.videonews.ui.base

interface BaseInit<T> {

    /**
     * 初始化View Binding
     * @return 初始化好的View Binding
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