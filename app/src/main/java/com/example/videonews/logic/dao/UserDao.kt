package com.example.videonews.logic.dao

import androidx.core.content.edit
import com.example.videonews.utils.Const
import com.example.videonews.utils.sharedPreferences

/**
 * @author Miracle
 * 用户本地持久化存储
 */
object UserDao {

    /**
     * 保存用户登录token函数
     */
    fun saveUserToken(token: String) {
        sharedPreferences(Const.USER).edit {
            putString(Const.USER_TOKEN, token)
        }
    }

    /**
     * 获取用户登录token函数
     * @return 用户登录token
     */
    fun getUserToken() = sharedPreferences(Const.USER).getString(Const.USER_TOKEN, "")!!

    /**
     * 清空用户登录token函数
     */
    fun clearUserToken() = sharedPreferences(Const.USER).edit {
        clear()
    }
}