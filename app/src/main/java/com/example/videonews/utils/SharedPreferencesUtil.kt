package com.example.videonews.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.videonews.VideoNewsApplication

/**
 * 获取sharedPreferences实例函数
 * @param name key
 * @return sharedPreferences实例
 */
fun sharedPreferences(name: String): SharedPreferences = VideoNewsApplication.mContext
    .getSharedPreferences(name, Context.MODE_PRIVATE)