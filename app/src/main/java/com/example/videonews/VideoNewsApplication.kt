package com.example.videonews

import android.app.Application
import android.content.Context

class VideoNewsApplication : Application() {

    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}