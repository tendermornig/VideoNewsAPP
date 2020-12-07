package com.example.videonews.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.example.videonews.logic.dao.UserDao
import com.example.videonews.logic.network.VideoNewsNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    private const val TAG = "Repository"

    fun userLogin(loginParam: Map<String, String>) = fire(Dispatchers.IO) {
        val loginResult = VideoNewsNetwork.userLogin(loginParam)
        Log.d(TAG, "userLogin: ")
        if ("success" == loginResult.msg && 200 == loginResult.code) {
            Result.success(loginResult.data)
        } else {
            Result.failure(
                RuntimeException(
                    "response status msg is ${loginResult.msg} code is ${loginResult.code}"
                )
            )
        }
    }

    private fun <T> fire(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> Result<T>
    ) = liveData(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun saveUserToken(token: String) = UserDao.saveUserToken(token)

    fun getUserToken() = UserDao.getUserToken()
}