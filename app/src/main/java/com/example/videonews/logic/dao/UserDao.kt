package com.example.videonews.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.videonews.VideoNewsApplication

object UserDao {

    private fun sharedPreferences() = VideoNewsApplication.mContext
        .getSharedPreferences("user", Context.MODE_PRIVATE)

    fun saveUserToken(token: String) {
        sharedPreferences().edit {
            putString("userToken", token)
        }
    }

    fun getUserToken() = sharedPreferences().getString("userToken", "")

    fun clearUserToken() = sharedPreferences().edit {
        clear()
    }
}