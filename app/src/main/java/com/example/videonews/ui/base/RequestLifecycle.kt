package com.example.videonews.ui.base

interface RequestLifecycle {

    /**
     * 开始加载
     */
    fun startLoading()

    /**
     * 加载完成
     */
    fun loadFinished()

    /**
     * 加载失败
     */
    fun loadFailed(msg: String?)
}