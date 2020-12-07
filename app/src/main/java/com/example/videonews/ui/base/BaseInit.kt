package com.example.videonews.ui.base

interface BaseInit {

    /**
     * 获取布局id
     */
    fun getLayoutId(): Int

    /**
     * 初始化视图
     */
    fun initView()

    /**
     * 初始化数据
     */
    fun initData()
}