package com.example.videonews.logic

import androidx.lifecycle.liveData
import com.example.videonews.logic.dao.UserDao
import com.example.videonews.logic.network.VideoNewsNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    private const val TAG = "Repository"

    fun userLogin(loginParam: Map<String, String>) = fire(Dispatchers.IO) {
        val loginResult = VideoNewsNetwork.userLogin(loginParam)
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

    fun userRegister(registerParam: Map<String, String>) = fire(Dispatchers.IO) {
        val registerResult = VideoNewsNetwork.userRegister(registerParam)
        if ("success" == registerResult.msg) {
            when (registerResult.code) {
                200 -> Result.success(true)
                401 -> Result.success(false)
                else -> Result.failure(
                    RuntimeException(
                        "response status msg is ${registerResult.msg} code is ${registerResult.code}"
                    )
                )
            }
        } else {
            Result.failure(
                RuntimeException(
                    "response status msg is ${registerResult.msg} code is ${registerResult.code}"
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
            Result.failure<T>(e)
        }
        emit(result)
    }

    fun saveUserToken(token: String) = UserDao.saveUserToken(token)

    fun getUserToken() = UserDao.getUserToken()
}