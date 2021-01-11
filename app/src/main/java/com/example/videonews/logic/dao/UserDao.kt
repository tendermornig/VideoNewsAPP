package com.example.videonews.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.videonews.VideoNewsApplication
import com.example.videonews.utils.Const

/**
 * @author Miracle
 * 用户本地持久化存储
 */
object UserDao {

    /**
     * 获取sharedPreferences实例函数
     * @return sharedPreferences实例
     */
    private fun sharedPreferences() = VideoNewsApplication.mContext
        .getSharedPreferences(Const.USER, Context.MODE_PRIVATE)

    /**
     * 保存用户登录token函数
     */
    fun saveUserToken(token: String) {
        sharedPreferences().edit {
            putString(Const.USER_TOKEN, token)
        }
    }

    /**
     * 获取用户登录token函数
     * @return 用户登录token
     */
    fun getUserToken() = sharedPreferences().getString(Const.USER_TOKEN, "")!!

    /**
     * 清空用户登录token函数
     */
    fun clearUserToken() = sharedPreferences().edit {
        clear()
    }
}