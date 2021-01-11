package com.example.videonews.logic.dao

import androidx.core.content.edit
import com.example.videonews.utils.Const
import com.example.videonews.utils.sharedPreferences

/**
 * @author Miracle
 * 资讯的本地持久化
 */
object NewsDao {

    /**
     * 保存资讯每次加载的个数
     * @param limit 资讯加载的个数
     */
    fun saveNewsLimit(limit: Int) {
        sharedPreferences(Const.NEWS).edit {
            putInt(Const.LIMIT, limit)
        }
    }

    /**
     * 获取资讯每次加载的个数
     * @return 用户登录token
     */
    fun getNewsLimit() = sharedPreferences(Const.NEWS).getInt(Const.LIMIT, 5)
}