package com.example.videonews.logic

import androidx.lifecycle.liveData
import com.example.videonews.logic.dao.NewsDao
import com.example.videonews.logic.dao.UserDao
import com.example.videonews.logic.network.VideoNewsNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * @author Miracle
 * 仓库层统一封装入口
 */
object Repository {

    fun userLogin(requestParam: Map<String, String>) = fire(Dispatchers.IO) {
        val loginResult = VideoNewsNetwork.userLogin(requestParam)
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

    fun userRegister(requestParam: Map<String, String>) = fire(Dispatchers.IO) {
        val registerResult = VideoNewsNetwork.userRegister(requestParam)
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

    fun getVideoList(category: Int) = fire {
        val videoListResult = VideoNewsNetwork.getVideoList(category, getUserToken())
        if ("success" == videoListResult.msg) {
            Result.success(videoListResult)
        } else {
            Result.failure(
                RuntimeException(
                    "response status msg is ${videoListResult.msg} code is ${videoListResult.code}"
                )
            )
        }
    }

    fun getVideoCategory(token: String) = fire {
        val categoryResult = VideoNewsNetwork.getVideoCategory(token)
        if ("success" == categoryResult.msg) {
            Result.success(categoryResult)
        } else {
            Result.failure(
                RuntimeException(
                    "response status msg is ${categoryResult.msg} code is ${categoryResult.code}"
                )
            )
        }
    }

    fun getNewsList(requestParam: Map<String, Int>) = fire {
        val newsResult = VideoNewsNetwork.getNewsList(requestParam, getUserToken())
        if ("success" == newsResult.msg) {
            Result.success(newsResult)
        } else {
            Result.failure(
                RuntimeException(
                    "response status msg is ${newsResult.msg} code is ${newsResult.code}"
                )
            )
        }
    }

    fun getNewsThumb(newsId: Int) = fire {
        val newsThumbResult = VideoNewsNetwork.getNewsThumb(newsId, getUserToken())
        if ("success" == newsThumbResult.msg) {
            Result.success(newsThumbResult)
        } else {
            Result.failure(
                RuntimeException(
                    "response status msg is ${newsThumbResult.msg} code is ${newsThumbResult.code}"
                )
            )
        }
    }

    /**
     * 通过高阶函数简化live data的异常处理
     * @param context 协程上下文
     * @param block 方法参数
     * @return 封装好返回值的live data对象
     */
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

    fun clearUserToken() = UserDao.clearUserToken()

    fun saveNewsLimit(limit: Int) = NewsDao.saveNewsLimit(limit)

    fun getNewsLimit() = NewsDao.getNewsLimit()
}