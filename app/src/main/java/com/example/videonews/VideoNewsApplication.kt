package com.example.videonews

import android.app.Application
import android.content.Context

class VideoNewsApplication : Application() {

    companion object {
        /**
         * 一个全局的上下文
         */
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}